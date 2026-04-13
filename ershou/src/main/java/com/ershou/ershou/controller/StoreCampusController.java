package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ershou.ershou.domain.po.StoreCampus;
import com.ershou.ershou.service.IStoreCampusService;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 校区信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-04-09
 */
@RestController
@RequestMapping("/store-campus")
public class StoreCampusController {

    @Autowired
    private IStoreCampusService storeCampusService;

    // 获取所有校区列表
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('usermanager:campus:search')")
    public AjaxResult getStoreCampusList() {
        List<StoreCampus> campusList = storeCampusService.list(
            new LambdaQueryWrapper<StoreCampus>()
                .eq(StoreCampus::getDeleted, false)
                .orderByAsc(StoreCampus::getCampusId)
        );
        return AjaxResult.success(campusList);
    }

    // 获取单个校区
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:campus:edit')")
    public AjaxResult getStoreCampusById(@PathVariable Integer id) {
        StoreCampus campus = storeCampusService.getById(id);
        if (campus == null || campus.getDeleted()) {
            return AjaxResult.error("校区不存在");
        }
        return AjaxResult.success(campus);
    }

    // 新增校区
    @PostMapping()
    @PreAuthorize("hasAuthority('usermanager:campus:add')")
    public AjaxResult addStoreCampus(@RequestBody StoreCampus storeCampus) {
        storeCampus.setCreateTime(LocalDateTime.now());
        storeCampus.setDeleted(false);
        boolean result = storeCampusService.save(storeCampus);
        if (!result) {
            return AjaxResult.error("新增校区失败");
        }
        return AjaxResult.success("新增校区成功");
    }

    // 修改校区
    @PutMapping()
    @PreAuthorize("hasAuthority('usermanager:campus:edit')")
    public AjaxResult updateStoreCampus(@RequestBody StoreCampus storeCampus) {
        if (storeCampus.getCampusId() == null) {
            return AjaxResult.error("校区ID不能为空");
        }
        boolean result = storeCampusService.updateById(storeCampus);
        if (!result) {
            return AjaxResult.error("修改校区失败");
        }
        return AjaxResult.success("修改校区成功");
    }

    // 删除校区
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:campus:delete')")
    public AjaxResult removeStoreCampus(@PathVariable Integer id) {
        LambdaUpdateWrapper<StoreCampus> updateWrapper = new LambdaUpdateWrapper<StoreCampus>()
                .eq(StoreCampus::getCampusId, id)
                .set(StoreCampus::getDeleted, true);
        boolean result = storeCampusService.update(updateWrapper);
        if (!result) {
            return AjaxResult.error("删除校区失败");
        }
        return AjaxResult.success("删除校区成功");
    }

    // 批量删除校区
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('usermanager:campus:deletes')")
    public AjaxResult removeStoreCampusBatch(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return AjaxResult.error("请选择要删除的校区");
        }
        LambdaUpdateWrapper<StoreCampus> updateWrapper = new LambdaUpdateWrapper<StoreCampus>()
                .in(StoreCampus::getCampusId, ids)
                .set(StoreCampus::getDeleted, true);
        boolean result = storeCampusService.update(updateWrapper);
        if (!result) {
            return AjaxResult.error("批量删除校区失败");
        }
        return AjaxResult.success("批量删除校区成功");
    }
}
