package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.dto.StoreGoodCommentDto;
import com.ershou.ershou.domain.po.StoreGoodComment;
import com.ershou.ershou.domain.query.StoreGoodCommentPageQuery;
import com.ershou.ershou.service.StoreGoodCommentService;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/comment")
public class StoreGoodCommentController {

    @Autowired
    private StoreGoodCommentService storeGoodCommentService;

    /**
     * 分页获取评论列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('usermanager:comment:list')")
    public AjaxResult getCommentList(StoreGoodCommentPageQuery query) {
        Page<StoreGoodCommentDto> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<StoreGoodCommentDto> commentList = storeGoodCommentService.getCommentList(page, query);
        PageResult<StoreGoodCommentDto> pageResult = new PageResult<>(commentList);
        return AjaxResult.success(pageResult);
    }

    /**
     * 删除/隐藏评论 (批量)
     */
    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('usermanager:comment:remove')")
    public AjaxResult removeComments(@RequestBody Map<String, List<Integer>> params) {
        List<Integer> ids = params.get("ids");
        if (ids == null || ids.isEmpty()) {
            return AjaxResult.error("请选择要删除的评论");
        }
        
        // 软删除，将 is_show 置为 0
        LambdaUpdateWrapper<StoreGoodComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(StoreGoodComment::getCommentId, ids)
                .set(StoreGoodComment::getIsShow, 0);
        
        boolean result = storeGoodCommentService.update(updateWrapper);
        
        if (result) {
            return AjaxResult.success("删除成功");
        } else {
            return AjaxResult.error("删除失败");
        }
    }

    /**
     * 屏蔽回复
     */
    @PostMapping("/removeReply")
    @PreAuthorize("hasAuthority('usermanager:comment:remove')")
    public AjaxResult removeReply(@RequestBody Map<String, Integer> params) {
        Integer commentId = params.get("commentId");
        if (commentId == null) {
            return AjaxResult.error("参数错误");
        }

        boolean result = storeGoodCommentService.removeReply(commentId);

        if (result) {
            return AjaxResult.success("屏蔽回复成功");
        } else {
            return AjaxResult.error("屏蔽回复失败");
        }
    }
}
