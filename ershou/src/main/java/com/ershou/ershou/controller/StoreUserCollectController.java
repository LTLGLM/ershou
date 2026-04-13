package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.dto.StoreUserCollectDto;
import com.ershou.ershou.domain.po.StoreUserCollect;
import com.ershou.ershou.domain.query.StoreUserCollectPageQuery;
import com.ershou.ershou.service.StoreUserCollectService;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-11-07
 */
@RestController
@RequestMapping("/storeUserCollect")
public class StoreUserCollectController {

    @Autowired
    private StoreUserCollectService storeUserCollectService;
    // 获取用户收藏列表
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('usermanager:collect:list')")
    public AjaxResult getStoreUserCollectList(StoreUserCollectPageQuery storeUserCollectPageQuery){
        // 获取用户收藏列表
        Page<StoreUserCollectDto> storeUserCollectList = storeUserCollectService.getStoreUserCollectList(storeUserCollectPageQuery);
        PageResult<StoreUserCollectDto> storeUserCollectDtoPageResult = new PageResult<>(storeUserCollectList);
        return AjaxResult.success(storeUserCollectDtoPageResult);
    }

    // 删除记录
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:collect:delete')")
    public AjaxResult deleteStoreUserCollect(@PathVariable Integer id){
        boolean deleteResult = storeUserCollectService.removeById(id);
        if (!deleteResult) return AjaxResult.error("删除记录失败");
        return AjaxResult.success("删除记录成功");
    }

    // 批量删除
    @DeleteMapping()
    @PreAuthorize("hasAuthority('usermanager:collect:deletes')")
    public AjaxResult deleteStoreUserCollects(@RequestBody List<Integer> ids){
        boolean deleteResult = storeUserCollectService.removeByIds(ids);
        if (!deleteResult) return AjaxResult.error("批量删除记录失败");
        return AjaxResult.success("批量删除记录成功");
    }

}