package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.dto.NoticeDto;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.AdminNotice;
import com.ershou.ershou.domain.po.Notice;
import com.ershou.ershou.domain.query.NoticePageQuery;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.service.AdminNoticeService;
import com.ershou.ershou.service.AdminService;
import com.ershou.ershou.service.NoticeService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.FieldFiller;
import com.ershou.ershou.utils.SecurityAdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AdminNoticeService adminNoticeService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 获取通知列表
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('systemmanager:notice:list')")
    public AjaxResult getNoticeList(NoticePageQuery noticePageQuery) {
        // 获取通知列表
        Page<NoticeDto> page = new Page<>(noticePageQuery.getPageNum(), noticePageQuery.getPageSize());
        Page<NoticeDto> noticeList = noticeService.getNoticeList(page, noticePageQuery);
        PageResult<NoticeDto> noticeDtoPageResult = new PageResult<>(noticeList);

        return AjaxResult.success(noticeDtoPageResult);
    }

    // 获取该用户的所有通知
    @GetMapping("/admin/{adminId}")
    public AjaxResult getAdminNoticeList(@PathVariable("adminId") Integer adminId) {
        // 获取该用户的所有未读通知
        LambdaQueryWrapper<AdminNotice> adminNoticeLambdaQueryWrapper = new LambdaQueryWrapper<AdminNotice>()
                .eq(AdminNotice::getAdminId, adminId)
                .eq(AdminNotice::getIsConfirm, 0)
                .select(AdminNotice::getNoticeId);
        List<Object> noticeIds = adminNoticeService.listObjs(adminNoticeLambdaQueryWrapper);

        if (noticeIds.isEmpty()) {
            return AjaxResult.success("该用户未接收任何通知", null);
        }

        LambdaQueryWrapper<Notice> noticeLambdaQueryWrapper = new LambdaQueryWrapper<Notice>()
                .in(Notice::getNoticeId, noticeIds)
                .orderByDesc(Notice::getCreateTime);
        List<Notice> noticeList = noticeService.list(noticeLambdaQueryWrapper);

        return AjaxResult.success("获取用户的全部通知", noticeList);
    }

    // 获取单个通知信息
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('systemmanager:notice:edit')")
    public AjaxResult getNoticeById(@PathVariable("id") Integer noticeId) {
        return AjaxResult.success(noticeService.getById(noticeId));
    }


    // 添加通知
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('systemmanager:notice:add')")
    @PostMapping()
    public AjaxResult addNotice(@RequestBody Notice notice) {
        if (notice == null) {
            return AjaxResult.error("新增通知信息为空");
        }
        if (notice.getNoticeTitle() == null || notice.getNoticeTitle().isEmpty()) {
            return AjaxResult.error("通知标题不能为空");
        }
        if (notice.getNoticeContent() == null || notice.getNoticeContent().isEmpty()) {
            return AjaxResult.error("通知内容不能为空");
        }

        // 设置创建用户和创建时间
        FieldFiller.addFillFields(notice);
        boolean addNoticeResult = noticeService.save(notice);

        if (!addNoticeResult) {
            throw new GeneralBusinessException("添加通知失败");
        }

        // 通知其他用户（自己除外）
        Integer adminId = SecurityAdminUtils.getAdmin().getAdmin().getAdminId();
        // 查询用户
        List<Admin> adminList = adminService.list(new LambdaQueryWrapper<Admin>().ne(Admin::getAdminId, adminId));

        ArrayList<AdminNotice> adminNotices = new ArrayList<>();
        for (Admin admin : adminList) {
            AdminNotice adminNotice = new AdminNotice();
            adminNotice.setNoticeId(notice.getNoticeId());
            adminNotice.setAdminId(admin.getAdminId());
            adminNotice.setIsConfirm(0);
            adminNotices.add(adminNotice);
        }
        // 添加到用户通知确认表中
        boolean noticeresult = adminNoticeService.saveBatch(adminNotices);
        if (!noticeresult) {
            throw new GeneralBusinessException("添加通知失败");
        }
        // 将通知存储到消息队列
        // 将消息发送到RabbitMQ
        // 参数说明:
        // 1. "noticeExchange" - 消息交换机名称，指定消息要发送到的交换器
        // 2. "notice.routing.key" - 路由键，用于消息路由到指定队列的匹配规则
        // 3. notice - 消息内容本体，这里是要发送的通知对象
        // 查询数据库最新的这条通知
        Notice newNotice = noticeService.getOne(new LambdaQueryWrapper<Notice>().eq(Notice::getNoticeId, notice.getNoticeId()).eq(Notice::getDelFlag, 0));

        HashMap<String, Object> noticeMessage = new HashMap<>();
        noticeMessage.put("type","noticeMessage");
        noticeMessage.put("status",200);
        noticeMessage.put("message",newNotice);
        noticeMessage.put("adminIds",adminList.stream().map(Admin::getAdminId).collect(Collectors.toList()));

        rabbitTemplate.convertAndSend("notice_exchange", "manager.notice.key", noticeMessage);

        return AjaxResult.success("添加通知成功");
    }

    // 修改通知
    @PreAuthorize("hasAuthority('systemmanager:notice:edit')")
    @PutMapping()
    public AjaxResult updateNotice(@RequestBody Notice notice) {
        // 设置更新用户和更新时间
        FieldFiller.updateFillFields(notice);

        boolean updateNoticeResult = noticeService.updateById(notice);

        if (!updateNoticeResult) {
            return AjaxResult.error("修改通知信息失败");
        }

        return AjaxResult.success("修改通知信息成功");
    }

    // 删除通知
    @PreAuthorize("hasAuthority('systemmanager:notice:delete')")
    @DeleteMapping("/{id}")
    public AjaxResult deleteNoticeById(@PathVariable("id") Integer noticeId) {

        LambdaUpdateWrapper<Notice> noticeLambdaUpdateWrapper = new LambdaUpdateWrapper<Notice>().eq(Notice::getNoticeId, noticeId)
                .set(Notice::getDelFlag, 1);

        boolean deleteNoticeResult = noticeService.update(noticeLambdaUpdateWrapper);

        if (!deleteNoticeResult) {
            return AjaxResult.error("删除通知信息失败");
        }

        return AjaxResult.success("删除通知信息成功");
    }

    // 批量删除通知
    @PreAuthorize("hasAuthority('systemmanager:notice:deletes')")
    @DeleteMapping()
    public AjaxResult deleteNoticeByIds(@RequestBody List<Integer> ids) {
        LambdaUpdateWrapper<Notice> noticeLambdaUpdateWrapper = new LambdaUpdateWrapper<Notice>().in(Notice::getNoticeId, ids).set(Notice::getDelFlag, 1);

        boolean deleteNoticeResult;
        try {
            deleteNoticeResult = noticeService.update(noticeLambdaUpdateWrapper);
        } catch (Exception e) {
            throw new GeneralBusinessException("批量删除通知失败");
        }

        if (!deleteNoticeResult) {
            return AjaxResult.error("删除通知信息失败");
        }

        return AjaxResult.success("删除通知信息成功");
    }
}
