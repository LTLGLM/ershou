package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.dto.StoreGoodDto;
import com.ershou.ershou.domain.po.StoreCate;
import com.ershou.ershou.domain.po.StoreGood;
import com.ershou.ershou.domain.query.StoreGoodPageQuery;
import com.ershou.ershou.service.StoreCateService;
import com.ershou.ershou.service.StoreGoodService;
import com.ershou.ershou.service.StoreUserService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.FieldFiller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-11-09
 */
@RestController
@RequestMapping("/storeGood")
public class StoreGoodController {

    @Autowired
    private StoreGoodService storeGoodService;

    @Autowired
    private StoreUserService storeUserService;

    @Autowired
    private StoreCateService storeCateService;


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('goodmanager:goods:list')")
    public AjaxResult getStoreGoodList(StoreGoodPageQuery storeGoodPageQuery) {
        if (storeGoodPageQuery.getFirstCateId() != null && storeGoodPageQuery.getSecondCateId() == null) {
            LambdaQueryWrapper<StoreCate> storeCateLambdaQueryWrapper = new LambdaQueryWrapper<StoreCate>().eq(StoreCate::getDelFlag, 0).eq(StoreCate::getCatePid, storeGoodPageQuery.getFirstCateId());
            List<Integer> cateids = storeCateService.list(storeCateLambdaQueryWrapper).stream().map(StoreCate::getCateId).collect(Collectors.toList());
            storeGoodPageQuery.setSelectCateId(cateids);

        }
        Page<StoreGoodDto> storeGoodList = storeGoodService.getStoreGoodList(storeGoodPageQuery);
        PageResult<StoreGoodDto> storeGoodDtoPageResult = new PageResult<>(storeGoodList);
        return AjaxResult.success(storeGoodDtoPageResult);
    }

    // 获取单个商品信息
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('goodmanager:goods:edit')")
    public AjaxResult getStoreGoodById(@PathVariable("id") Integer storeGoodId) {
        StoreGood storeGood = storeGoodService.getById(storeGoodId);

        Integer userId = storeGood.getUserId();
        Integer cateId = storeGood.getCateId();

        StoreGoodDto storeGoodDto = new StoreGoodDto();
        BeanUtils.copyProperties(storeGood, storeGoodDto);

        storeGoodDto.setUsername(storeUserService.getById(userId).getUsername());
        storeGoodDto.setCateName(storeCateService.getById(cateId).getCateName());
        return AjaxResult.success(storeGoodDto);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('goodmanager:goods:add')")
    public AjaxResult addStoreGood(@RequestBody StoreGoodDto storeGoodDto) {
        long count = storeGoodService.count(new LambdaQueryWrapper<StoreGood>().eq(StoreGood::getGoodName, storeGoodDto.getGoodName()));
        if(count > 0){
            return AjaxResult.error("添加失败!!! 该商品名称已存在");
        }
        // TODO 添加所属分类
        // Integer cateId = storeGood.getCateId();

        // 设置添加时间
        FieldFiller.addFillFields(storeGoodDto);
        boolean addStoreGoodResult = storeGoodService.save(storeGoodDto);
        if (!addStoreGoodResult) {
            return AjaxResult.error("添加商品失败");
        }
        return AjaxResult.success("添加商品成功");
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('goodmanager:goods:edit')")
    public AjaxResult updateStoreGood(@RequestBody StoreGoodDto storeGoodDto) {
        if (storeGoodDto.getGoodId() == null) {
            return AjaxResult.error("更新失败!!!请提供商品ID");
        }
        FieldFiller.updateFillFields(storeGoodDto);
        boolean upodatestoreGoodResult = storeGoodService.updateById(storeGoodDto);
        if (!upodatestoreGoodResult) {
            return AjaxResult.error("更新商品失败");
        }
        return AjaxResult.success("更新商品成功");
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('goodmanager:goods:delete')")
    public AjaxResult deleteStoreGood(@PathVariable("id") Integer storeGoodId) {
        LambdaUpdateWrapper<StoreGood> storeGoodLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreGood>().eq(StoreGood::getGoodId, storeGoodId).set(StoreGood::getIsDel, 1);
        boolean deleteResult = storeGoodService.update(storeGoodLambdaUpdateWrapper);
        if (!deleteResult) return AjaxResult.error("删除商品失败");
        return AjaxResult.success("删除商品成功");
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('goodmanager:goods:deletes')")
    public AjaxResult deleteStoreGoods(@RequestBody List<Integer> ids) {
        LambdaUpdateWrapper<StoreGood> storeGoodLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreGood>().in(StoreGood::getGoodId, ids).set(StoreGood::getIsDel, 1);
        boolean deleteStoreGoodResult = storeGoodService.update(storeGoodLambdaUpdateWrapper);
        if (!deleteStoreGoodResult) return AjaxResult.error("批量删除商品失败");
        return AjaxResult.success("批量删除商品成功");
    }

    // 上下架商品
    @PutMapping("/isShow")
    @PreAuthorize("hasAuthority('goodmanager:goods:edit')")
    public AjaxResult updateStoreGoodStatus(@RequestBody StoreGoodDto storeGoodDto) {
        if (storeGoodDto.getGoodId() == null) {
            return AjaxResult.error("操作失败!!!请提供商品ID");
        }
        StoreGood storeGood = storeGoodService.getById(storeGoodDto.getGoodId());
        if(storeGood == null){
            return AjaxResult.error("操作失败!!! 该商品不存在");
        }
        storeGood.setIsShow(storeGoodDto.getIsShow());
        boolean updateStoreGoodResult = storeGoodService.updateById(storeGood);
        if (!updateStoreGoodResult) {
            return AjaxResult.error("商品上下架操作失败");
        }
        return AjaxResult.success("商品上下架操作成功");
    }
}
