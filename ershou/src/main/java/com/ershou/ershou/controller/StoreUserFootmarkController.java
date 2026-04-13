package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.dto.StoreUserCollectDto;
import com.ershou.ershou.domain.dto.StoreUserFootmarkDto;
import com.ershou.ershou.domain.query.StoreUserCollectPageQuery;
import com.ershou.ershou.service.StoreUserFootmarkService;
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
@RequestMapping("/storeUserFootmark")
public class StoreUserFootmarkController {
    @Autowired
    private StoreUserFootmarkService storeUserFootmarkService;

    // 获取用户浏览足迹列表
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('usermanager:footmark:list')")
    public AjaxResult getStoreUserFootmarkList(StoreUserCollectPageQuery storeUserCollectPageQuery){
        // 获取用户足迹列表
        Page<StoreUserFootmarkDto> storeUserCollectList = storeUserFootmarkService.getStoreUserFootmarkList(storeUserCollectPageQuery);
        PageResult<StoreUserFootmarkDto> storeUserCollectDtoPageResult = new PageResult<>(storeUserCollectList);
        return AjaxResult.success(storeUserCollectDtoPageResult);
    }

    // 删除单个用户
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:footmark:delete')")
    public AjaxResult deleteStoreUserFootmarkById(@PathVariable("id") Integer id){
        boolean deleteResult = storeUserFootmarkService.removeById(id);
        if (!deleteResult) return AjaxResult.error("删除用户浏览足迹失败");
        return AjaxResult.success("删除用户浏览足迹成功");
    }


    // 批量删除多个用户
    @DeleteMapping()
    @PreAuthorize("hasAuthority('usermanager:footmark:deletes')")
    public AjaxResult deleteStoreUserFootmarkByIds(@RequestBody List<Integer> ids){
        boolean removeResult = storeUserFootmarkService.removeByIds(ids);
        if (!removeResult) return AjaxResult.error("批量删除用户浏览足迹失败");
        return AjaxResult.success("批量删除用户浏览足迹成功");
    }
}
