package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.po.StoreFloor;
import com.ershou.ershou.domain.query.StoreFloorPageQuery;
import com.ershou.ershou.service.StoreFloorService;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 校园楼表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-11-07
 */
@RestController
@RequestMapping("/storeFloor")
public class StoreFloorController {

    @Autowired
    private StoreFloorService storeFloorService;

    // 获取校园楼管理列表
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('usermanager:floor:list')")
    public AjaxResult getStoreFloorList(StoreFloorPageQuery storeFloorPageQuery){
        LambdaQueryWrapper<StoreFloor> storeFloorLambdaQueryWrapper = new LambdaQueryWrapper<StoreFloor>().eq(StoreFloor::getDeleted, 0);
        if(storeFloorPageQuery.getFloorName() != null && !storeFloorPageQuery.getFloorName().isEmpty()){
            storeFloorLambdaQueryWrapper.like(StoreFloor::getFloorName, storeFloorPageQuery.getFloorName());
        }
        if(storeFloorPageQuery.getStarttime() != null && !storeFloorPageQuery.getStarttime().isEmpty()){
            storeFloorLambdaQueryWrapper.ge(StoreFloor::getCreateTime, storeFloorPageQuery.getStarttime());
        }
        if(storeFloorPageQuery.getEndtime() != null && !storeFloorPageQuery.getEndtime().isEmpty()){
            storeFloorLambdaQueryWrapper.le(StoreFloor::getCreateTime, storeFloorPageQuery.getEndtime());
        }
        Page<StoreFloor> storeFloorPage = new Page<>(storeFloorPageQuery.getPageNum(), storeFloorPageQuery.getPageSize());
        Page<StoreFloor> page = storeFloorService.page(storeFloorPage, storeFloorLambdaQueryWrapper);
        PageResult<StoreFloor> storeFloorPageResult = new PageResult<>(page);
        return AjaxResult.success(storeFloorPageResult);
    }

    // 获取校园楼全部
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('usermanager:floor:list')")
    public AjaxResult getAllStoreFloor(){
        List<StoreFloor> storeFloorList = storeFloorService.list(new LambdaQueryWrapper<StoreFloor>().eq(StoreFloor::getDeleted, 0));
        return AjaxResult.success(storeFloorList);
    }


    // 获取单个校园楼
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:floor:edit')")
    public AjaxResult getStoreFloorById(@PathVariable Integer id){
        return AjaxResult.success(storeFloorService.getById(id));
    }

    // 新增校园楼
    @PostMapping()
    @PreAuthorize("hasAuthority('usermanager:floor:add')")
    public AjaxResult addStoreFloor(@RequestBody StoreFloor storeFloor){
        storeFloor.setCreateTime(LocalDateTime.now());
        boolean addStoreFloorResult = storeFloorService.saveOrUpdate(storeFloor);
        if(!addStoreFloorResult){
            return AjaxResult.error("新增校园楼失败");
        }
        return AjaxResult.success("新增校园楼成功");
    }


    // 修改校园楼
    @PutMapping()
    @PreAuthorize("hasAuthority('usermanager:floor:edit')")
    public AjaxResult updateStoreFloorById(@RequestBody StoreFloor storeFloor){
        boolean updateStoreFloorResult = storeFloorService.updateById(storeFloor);
        if(!updateStoreFloorResult){
            return AjaxResult.error("修改校园楼失败");
        }
        return AjaxResult.success("修改校园楼成功");
    }

    // 删除校园楼
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:floor:delete')")
    public AjaxResult removeStoreFloorById(@PathVariable Integer id){
        LambdaUpdateWrapper<StoreFloor> storeFloorLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreFloor>().eq(StoreFloor::getFloorId, id).set(StoreFloor::getDeleted, 1);
        boolean removeStoreFloorResult = storeFloorService.update(storeFloorLambdaUpdateWrapper);
        if(!removeStoreFloorResult){
            return AjaxResult.error("删除校园楼失败");
        }
        return AjaxResult.success("删除校园楼成功");
    }

    // 批量删除校园楼
    @DeleteMapping()
    @PreAuthorize("hasAuthority('usermanager:floor:deletes')")
    public AjaxResult removeStoreFloors(@RequestBody List<Integer> ids){
        LambdaUpdateWrapper<StoreFloor> storeFloorLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreFloor>().in(StoreFloor::getFloorId, ids).set(StoreFloor::getDeleted, 1);
        boolean removeStoreFloorsResult = storeFloorService.update(storeFloorLambdaUpdateWrapper);
        if (!removeStoreFloorsResult) return AjaxResult.error("批量删除校园楼失败");
        return AjaxResult.success("批量删除校园楼成功");
    }
}
