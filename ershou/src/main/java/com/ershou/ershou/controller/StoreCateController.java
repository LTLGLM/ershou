package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ershou.ershou.domain.dto.StoreCateDto;
import com.ershou.ershou.domain.po.StoreCate;
import com.ershou.ershou.service.StoreCateService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.FieldFiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */
@RestController
@RequestMapping("/storeCate")
public class StoreCateController {

    @Autowired
    private StoreCateService storeCateService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('goodmanager:category:list')")
    public AjaxResult getStoreCateList(StoreCate storeCate) {
        LambdaQueryWrapper<StoreCate> storeCateLambdaQueryWrapper = new LambdaQueryWrapper<StoreCate>().eq(StoreCate::getDelFlag, 0)
                .eq(storeCate.getCateId() != null,StoreCate::getCateId,storeCate.getCateId())
                .eq(storeCate.getCateStatus() != null,StoreCate::getCateStatus,storeCate.getCateStatus())
                .like(storeCate.getCateName() != null,StoreCate::getCateName, storeCate.getCateName())
                .orderByAsc(StoreCate::getCatePid);
        List<StoreCate> storeCates = storeCateService.list(storeCateLambdaQueryWrapper);
        return AjaxResult.success(storeCates);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('goodmanager:category:edit')")
    public AjaxResult getStoreCateById(@PathVariable("id") Integer storeCateId) {
        StoreCateDto storeCateByid = storeCateService.getStoreCateByid(storeCateId);
        return AjaxResult.success(storeCateByid);
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('goodmanager:category:add')")
    public AjaxResult addStoreCate(@RequestBody StoreCate storeCate) {
        FieldFiller.addFillFields(storeCate);
        if(storeCate.getCatePid() == null){
            storeCate.setCatePid(0);
        }
        boolean addStoreCateResult = storeCateService.save(storeCate);
        if(!addStoreCateResult){
            return AjaxResult.error("添加分类失败");
        }
        return AjaxResult.success("添加分类成功");
    }


    @PutMapping()
    @PreAuthorize("hasAuthority('goodmanager:category:edit')")
    public AjaxResult updateStoreCate(@RequestBody StoreCate storeCate) {
        boolean updateStoreCateResult = storeCateService.updateById(storeCate);
        if (!updateStoreCateResult) {
            return AjaxResult.error("更新分类失败");
        }
        return AjaxResult.success("更新分类成功");
    }

    // @DeleteMapping({"id"})
    // public AjaxResult deleteStoreCateById(@PathVariable("id") Integer storeCateId) {
    //     LambdaUpdateWrapper<StoreCate> storeCateLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreCate>().eq(StoreCate::getCateId, storeCateId).set(StoreCate::getDelFlag, 1);
    //     boolean deleteStoreCateResult = storeCateService.update(storeCateLambdaUpdateWrapper);
    //     if (!deleteStoreCateResult) return AjaxResult.error("删除记录失败");
    //     return AjaxResult.success("删除记录成功");
    // }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('goodmanager:category:delete')")
    public AjaxResult deleteStoreCate(@RequestBody List<Integer> ids) {
        LambdaUpdateWrapper<StoreCate> storeCateLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreCate>().in(StoreCate::getCateId, ids).set(StoreCate::getDelFlag, 1);
        boolean deleteStoreCatesResult = storeCateService.update(storeCateLambdaUpdateWrapper);
        if (!deleteStoreCatesResult) return AjaxResult.error("删除分类失败");
        return AjaxResult.success("删除分类成功");
    }

}
