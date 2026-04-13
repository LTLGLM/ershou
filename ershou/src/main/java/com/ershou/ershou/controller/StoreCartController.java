package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ershou.ershou.domain.dto.StoreCartDto;
import com.ershou.ershou.domain.dto.StoreCartGoodDto;
import com.ershou.ershou.domain.po.StoreCart;
import com.ershou.ershou.domain.po.StoreCartInfo;
import com.ershou.ershou.domain.po.StoreGood;
import com.ershou.ershou.service.IStoreCartInfoService;
import com.ershou.ershou.service.IStoreCartService;
import com.ershou.ershou.service.StoreGoodService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车管理接口
 */
@RestController
@RequestMapping("/storeCart")
public class StoreCartController {

    @Resource
    private IStoreCartService storeCartService;

    @Resource
    private IStoreCartInfoService storeCartInfoService;

    @Resource
    private StoreGoodService storeGoodService;

    /**
     * 获取用户购物车列表
     */
    @GetMapping("/list")
    public AjaxResult getCartList(@RequestParam("uid") Integer uid) {
        StoreCart storeCart = storeCartService.getOne(new LambdaQueryWrapper<StoreCart>().eq(StoreCart::getUid, uid).eq(StoreCart::getStatus, 0));// 仅获取有效状态的购物车项)
        if (storeCart == null) {
            return AjaxResult.error("购物车为空");
        }
        Long cartId = storeCart.getId();
        // 查询到购物车详情列表
        List<StoreCartInfo> list = storeCartInfoService.list(new LambdaQueryWrapper<StoreCartInfo>().eq(StoreCartInfo::getCartId, cartId));
        StoreCartDto storeCartDto = BeanCopyUtils.copyProperties(storeCart, StoreCartDto.class);
        // 根据详情列表获取购物车的商品信息
        List<StoreCartGoodDto> storeCartGoodList = list.stream().map(item -> {
            StoreGood storeGood = storeGoodService.getById(item.getGoodId());
            StoreCartGoodDto storeCartGoodDto = BeanCopyUtils.copyProperties(storeGood, StoreCartGoodDto.class);
            storeCartGoodDto.setChecked(item.getChecked());
            storeCartGoodDto.setAddNum(item.getAddNum());
            storeCartGoodDto.setCartInfoId(item.getId());
            return storeCartGoodDto;
        }).collect(Collectors.toList());
        storeCartDto.setStoreCartGoodList(storeCartGoodList);

        return AjaxResult.success(storeCartDto);
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping("/add/{userId}/{goodId}")
    public AjaxResult addToCart(@PathVariable("userId") String userId, @PathVariable("goodId") String goodId) {
        // 先检查购物车是否存在
        StoreCart cart = storeCartService.getOne(new LambdaQueryWrapper<StoreCart>().eq(StoreCart::getUid, userId));
        if (cart == null) {
            // 如果购物车为空
            // 先创建
            cart = new StoreCart();
            cart.setUid(Integer.valueOf(userId));
            cart.setStatus(0);
            cart.setCreateTime(LocalDateTime.now());
            cart.setUpdateTime(LocalDateTime.now());
            boolean added = storeCartService.save(cart);

            if (!added) {
                return AjaxResult.error("创建购物车失败");
            }
        }

        StoreCartInfo storeCartInfo = storeCartInfoService.getOne(new LambdaQueryWrapper<StoreCartInfo>().eq(StoreCartInfo::getCartId, cart.getId()));

        if (storeCartInfo != null) {
            // 已存在，则更新数量
            storeCartInfo.setAddNum(storeCartInfo.getAddNum() + 1);
            storeCartInfo.setUpdateTime(LocalDateTime.now());
            boolean updated = storeCartInfoService.updateById(storeCartInfo);
            return updated ? AjaxResult.success("商品数量已更新") : AjaxResult.error("更新失败");
        } else {
            StoreCartInfo newStoreCartInfo = new StoreCartInfo();
            newStoreCartInfo.setCartId(cart.getId());
            newStoreCartInfo.setChecked(0);
            newStoreCartInfo.setAddNum(1);
            newStoreCartInfo.setGoodId(Integer.valueOf(goodId));
            newStoreCartInfo.setCreateTime(LocalDateTime.now());
            newStoreCartInfo.setUpdateTime(LocalDateTime.now());
            boolean added = storeCartInfoService.save(newStoreCartInfo);
            return added ? AjaxResult.success("添加商品成功") : AjaxResult.error("添加商品失败");
        }
    }

    /**
     * 更新购物车中的商品数量
     */
    @PutMapping("/update")
    public AjaxResult updateCart(@RequestBody StoreCartInfo storeCartInfo) {
        storeCartInfo.setUpdateTime(LocalDateTime.now());
        boolean updated = storeCartInfoService.updateById(storeCartInfo);
        return updated ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    /**
     * 从购物车中移除商品
     */
    @DeleteMapping("/remove/{cartInfoId}")
    public AjaxResult removeFromCart(@PathVariable("cartInfoId") Integer cartInfoId) {
        boolean remove = storeCartInfoService.remove(new LambdaQueryWrapper<StoreCartInfo>().eq(StoreCartInfo::getId, cartInfoId));
        return remove ? AjaxResult.success("删除成功") : AjaxResult.error("删除失败");
    }

    // 全选/全不选
    @PutMapping("/update/checkAll")
    public AjaxResult checkAll(@RequestBody StoreCartInfo storeCartInfo){
        boolean update = storeCartInfoService.update(new LambdaUpdateWrapper<StoreCartInfo>().set(StoreCartInfo::getChecked, storeCartInfo.getChecked()));
        return update ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    /**
     * 批量删除购物车项（软删除）
     */
    @DeleteMapping("/removeBatch")
    public AjaxResult removeBatch(@RequestBody List<Long> cartInfoIds) {
        if (cartInfoIds == null || cartInfoIds.isEmpty()) {
            return AjaxResult.error("请提供要删除的购物车项 ID 列表");
        }
        boolean remove = storeCartInfoService.remove(new LambdaQueryWrapper<StoreCartInfo>().in(StoreCartInfo::getId, cartInfoIds));
        return remove ? AjaxResult.success("批量删除成功") : AjaxResult.error("批量删除失败");
    }
}
