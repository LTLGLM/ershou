package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.dto.StoreAddressDto;
import com.ershou.ershou.domain.po.StoreAddress;
import com.ershou.ershou.domain.query.StoreAddressPageQuery;
import com.ershou.ershou.service.StoreAddressService;
import com.ershou.ershou.service.StoreFloorService;
import com.ershou.ershou.service.StoreUserService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 收货地址表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
@RestController
@RequestMapping("/storeAddress")
public class StoreAddressController {

    @Autowired
    private StoreAddressService storeAddressService;

    @Autowired
    private StoreUserService storeUserService;

    @Autowired
    private StoreFloorService storeFloorService;

    // 获取收货地址列表
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('usermanager:address:list')")
    public AjaxResult getStoreAddressList(StoreAddressPageQuery storeAddressPageQuery) {
        Page<StoreAddressDto> storeAddressList = storeAddressService.getStoreAddressList(storeAddressPageQuery);
        PageResult<StoreAddressDto> storeAddressDtoPageResult = new PageResult<>(storeAddressList);
        return AjaxResult.success(storeAddressDtoPageResult);
    }

    // 获取单个收货地址
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:address:edit')")
    public AjaxResult getStoreAddressById(@PathVariable Integer id) {
        StoreAddress address = storeAddressService.getById(id);
        String username = storeUserService.getById(address.getUserId()).getUsername();
        String floorName = storeFloorService.getById(address.getFloorId()).getFloorName();
        StoreAddressDto storeAddressDto = BeanCopyUtils.copyProperties(address, StoreAddressDto.class);
        storeAddressDto.setUsername(username);
        storeAddressDto.setFloorName(floorName);
        return AjaxResult.success(storeAddressDto);
    }

    // 修改收货地址
    @PutMapping
    @PreAuthorize("hasAuthority('usermanager:address:edit')")
    public AjaxResult updateStoreAddress(@RequestBody StoreAddressDto storeAddressDto) {
        storeAddressDto.setUpdateTime(LocalDateTime.now());
        boolean updateResult = storeAddressService.updateById(storeAddressDto);
        if (!updateResult) return AjaxResult.error("修改收货地址失败");
        return AjaxResult.success("修改收货地址成功");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:address:delete')")
    public AjaxResult deleteStoreAddressById(@PathVariable Integer id) {
        LambdaUpdateWrapper<StoreAddress> storeAddressLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreAddress>().eq(StoreAddress::getAddressId, id).set(StoreAddress::getDeleted, 1);
        boolean deleteResult = storeAddressService.update(storeAddressLambdaUpdateWrapper);
        if (!deleteResult) return AjaxResult.error("删除收货地址失败");
        return AjaxResult.success("删除收货地址成功");
    }

    // 批量删除收货地址
    @DeleteMapping()
    @PreAuthorize("hasAuthority('usermanager:address:deletes')")
    public AjaxResult deleteStoreAddressByIds(@RequestBody List<Integer> ids) {
        LambdaUpdateWrapper<StoreAddress> storeAddressLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreAddress>().in(StoreAddress::getAddressId, ids).set(StoreAddress::getDeleted, 1);
        boolean deleteResult = storeAddressService.update(storeAddressLambdaUpdateWrapper);
        if (!deleteResult) return AjaxResult.error("批量删除收货地址失败");
        return AjaxResult.success("批量删除收货地址成功");
    }


    // 设置默认收获地址
    @PutMapping("/default")
    @PreAuthorize("hasAuthority('usermanager:address:edit')")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult setDefaultStoreAddress(@RequestBody StoreAddressDto storeAddressDto) {
        // 将该用户的默认地址改成不默认
        LambdaUpdateWrapper<StoreAddress> defaultSetNo = new LambdaUpdateWrapper<StoreAddress>()
                .eq(StoreAddress::getDeleted, 0)
                .eq(StoreAddress::getUserId, storeAddressDto.getUserId())
                .set(StoreAddress::getIsDefault, 1);
        boolean updateResult = storeAddressService.update(defaultSetNo);

        // 设置该用户的默认地址
        LambdaUpdateWrapper<StoreAddress> storeAddressLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreAddress>()
                .eq(StoreAddress::getDeleted, 0)
                .eq(StoreAddress::getAddressId, storeAddressDto.getAddressId())
                .set(StoreAddress::getIsDefault, 0);

        boolean updateAddressDefault = storeAddressService.update(storeAddressLambdaUpdateWrapper);

        if (!updateResult || !updateAddressDefault) {
            return AjaxResult.error("更新默认地址失败");
        }

        return AjaxResult.success("更新默认地址成功");
    }
}
