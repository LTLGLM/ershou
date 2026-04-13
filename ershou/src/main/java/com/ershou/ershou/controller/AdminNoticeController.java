package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ershou.ershou.domain.po.AdminNotice;
import com.ershou.ershou.domain.po.Notice;
import com.ershou.ershou.service.AdminNoticeService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.SecurityAdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-02-03
 */
@RestController
@RequestMapping("/adminNotice")
public class AdminNoticeController {

    @Autowired
    private AdminNoticeService adminNoticeService;

    // 修改用户通知状态
    @PutMapping("/status")
    public AjaxResult updateConfirmStatus(@RequestBody Notice notice) {
        Integer adminId = SecurityAdminUtils.getAdmin().getAdmin().getAdminId();
        // TODO: 2025-02-03 实现修改用户通知状态
        boolean updateIsConfirm = adminNoticeService.update(new LambdaUpdateWrapper<AdminNotice>()
                .eq(AdminNotice::getAdminId, adminId)
                .eq(AdminNotice::getNoticeId, notice.getNoticeId())
                .set(AdminNotice::getConfirmTime, new Date())
                .set(AdminNotice::getIsConfirm, 1));

        if (!updateIsConfirm) {
            return AjaxResult.error("用户通知状态更新失败");
        }

        return AjaxResult.success("用户通知状态更新成功");
    }

    @PutMapping("/allStatus")
    public AjaxResult updateAllConfirmStatus() {

        Integer adminId = SecurityAdminUtils.getAdmin().getAdmin().getAdminId();
        // TODO: 2025-02-03 实现修改所有用户通知状态
        boolean updateIsConfirm = adminNoticeService.update(new LambdaUpdateWrapper<AdminNotice>()
                .eq(AdminNotice::getAdminId, adminId)
                .eq(AdminNotice::getIsConfirm, 0)
                .set(AdminNotice::getConfirmTime, new Date())
                .set(AdminNotice::getIsConfirm, 1));

        return AjaxResult.success("所有用户通知状态更新成功");
    }

}
