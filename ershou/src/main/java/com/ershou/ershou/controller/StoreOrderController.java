package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ershou.ershou.domain.po.*;
import com.ershou.ershou.service.*;
import com.ershou.ershou.service.impl.StoreCartServiceImpl;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-04-06
 */
@RestController
@RequestMapping("/store-order")
public class StoreOrderController {

    @Autowired
    private IStoreOrderService storeOrderService;

    @Autowired
    private StoreAddressService storeAddressService;
    
    @Autowired
    private StoreGoodService storeGoodService;
    
    @Autowired
    private StoreUserService storeUserService;
    
    @Autowired
    private StoreCartServiceImpl storeCartService;

    @Autowired
    private IStoreCartInfoService storeCartInfoService;

    @Autowired
    private IStoreCampusService storeCampusService;

    @Autowired
    private StoreFloorService storeFloorService;

    @PostMapping("/create/{cardId}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult createOrder(@PathVariable Integer cardId, @RequestBody(required = false) Map<String, Object> params) {
        // 1. 查询购物车商品
        List<StoreCartInfo> storeCartInfos = storeCartInfoService.list(
            new LambdaQueryWrapper<StoreCartInfo>()
                .eq(StoreCartInfo::getCartId, cardId)
                .eq(StoreCartInfo::getChecked, 1) // 只处理选中的商品
        );
        
        if (storeCartInfos.isEmpty()) {
            return AjaxResult.error("购物车中没有选中的商品");
        }

        // 2. 生成统一的订单号
        String orderId = UUID.randomUUID().toString().replace("-", "");
        
        // 3. 获取用户信息
        Integer userId = storeCartService.getById(storeCartInfos.get(0).getCartId()).getUid();
        StoreUser user = storeUserService.getById(userId);
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }
        
        // 4. 获取收货地址
        Integer addressId = null;
        if (params != null && params.containsKey("addressId")) {
            addressId = (Integer) params.get("addressId");
        } else {
            // 如果没有传入地址ID，则获取默认地址
            List<StoreAddress> addressList = storeAddressService.list(
                new LambdaQueryWrapper<StoreAddress>()
                    .eq(StoreAddress::getUserId, userId)
                    .eq(StoreAddress::getIsDefault, 0)
            );
            addressId = addressList.isEmpty() ? null : addressList.get(0).getAddressId();
        }

        // 5. 处理每个商品，生成独立的订单记录
        for (StoreCartInfo cartInfo : storeCartInfos) {
            // 获取商品信息
            StoreGood good = storeGoodService.getById(cartInfo.getGoodId());
            if (good == null) {
                return AjaxResult.error("商品不存在");
            }
            if (!good.getIsShow()) {
                return AjaxResult.error("商品已下架");
            }
            if (good.getStock() < cartInfo.getAddNum()) {
                return AjaxResult.error("商品库存不足");
            }
            
            // 计算单个商品的总价
            BigDecimal totalPrice = good.getPrice().multiply(new BigDecimal(cartInfo.getAddNum()));
            
            // 创建订单记录
            StoreOrder order = new StoreOrder();
            order.setOrderId(orderId); // 使用相同的订单号
            order.setUid(userId);
            order.setRealName(user.getUsername());
            order.setUserPhone(user.getMobile());
            order.setAddressId(addressId);
            order.setTotalNum(cartInfo.getAddNum()); // 单个商品的数量
            order.setTotalPrice(totalPrice); // 单个商品的总价
            order.setPaid(0); // 未支付
            order.setCreateTime(LocalDateTime.now());
            order.setStatus(0); // 待发货
            order.setIsDel(0);
            order.setIsSystemDel(false);
            order.setUpdateTime(LocalDateTime.now());
            order.setGoodId(good.getGoodId()); // 记录商品ID
            order.setMerId(good.getUserId()); // 记录卖家ID
            
            // 保存订单
            storeOrderService.save(order);
            
            // 更新商品库存
            good.setStock(good.getStock() - cartInfo.getAddNum());
            storeGoodService.updateById(good);
            
            // 删除购物车中的商品
            storeCartInfoService.removeById(cartInfo.getId());
        }

        return AjaxResult.success("操作成功",orderId);
    }

    @GetMapping("/{orderId}")
    public AjaxResult getOrderDetail(@PathVariable String orderId) {
        // 1. 查询所有相关订单记录
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getOrderId, orderId)
        );
        
        if (orders.isEmpty()) {
            return AjaxResult.error("订单不存在");
        }
        
        // 2. 计算订单总价、总数量和总跑腿费
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalFreightPrice = BigDecimal.ZERO;
        int totalNum = 0;
        List<Map<String, Object>> goodsList = new ArrayList<>();
        
        for (StoreOrder order : orders) {
            totalPrice = totalPrice.add(order.getTotalPrice());
            totalFreightPrice = totalFreightPrice.add(order.getFreightPrice());
            totalNum += order.getTotalNum();
            
            // 获取商品信息
            StoreGood good = storeGoodService.getById(order.getGoodId());
            if (good != null) {
                Map<String, Object> goodMap = new HashMap<>();
                goodMap.put("goodId", good.getGoodId());
                goodMap.put("goodName", good.getGoodName());
                goodMap.put("goodImage", good.getImage());
                goodMap.put("price", good.getPrice());
                goodMap.put("quantity", order.getTotalNum());
                goodMap.put("subtotal", order.getTotalPrice());
                goodMap.put("freightPrice", order.getFreightPrice());
                goodsList.add(goodMap);
            }
        }
        
        // 3. 获取买家信息
        StoreOrder firstOrder = orders.get(0);
        StoreUser user = storeUserService.getById(firstOrder.getUid());
        String userName = user != null ? user.getUsername() : "";
        
        // 4. 获取默认收货地址
        StoreAddress defaultAddress = null;
        if (firstOrder.getAddressId() != null) {
            defaultAddress = storeAddressService.getById(firstOrder.getAddressId());
        } else {
            // 如果没有地址ID，查询用户的默认地址
            List<StoreAddress> addressList = storeAddressService.list(
                new LambdaQueryWrapper<StoreAddress>()
                    .eq(StoreAddress::getUserId, firstOrder.getUid())
                    .eq(StoreAddress::getIsDefault, 0)
            );
            if (!addressList.isEmpty()) {
                defaultAddress = addressList.get(0);
            }
        }
        
        // 5. 组装返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("orderStatus", firstOrder.getStatus());
        result.put("paid", firstOrder.getPaid());
        result.put("totalPrice", totalPrice); // 商品总价
        result.put("totalFreightPrice", totalFreightPrice); // 总跑腿费
        result.put("totalAmount", totalPrice.add(totalFreightPrice)); // 订单总金额（商品总价+跑腿费）
        result.put("totalNum", totalNum);
        result.put("createTime", firstOrder.getCreateTime());
        result.put("payTime", firstOrder.getPayTime());
        result.put("payType", firstOrder.getPayType());
        result.put("userName", userName);
        result.put("userPhone", firstOrder.getUserPhone());
        result.put("goodsList", goodsList);
        result.put("remark", firstOrder.getRemark());
        result.put("mark", firstOrder.getMark());
        
        // 添加地址信息
        if (defaultAddress != null) {
            Map<String, Object> addressInfo = new HashMap<>();
            addressInfo.put("addressId", defaultAddress.getAddressId());
            addressInfo.put("name", defaultAddress.getName());
            addressInfo.put("phone", defaultAddress.getTel());
            addressInfo.put("addressDetail", defaultAddress.getAddressDetail());
            addressInfo.put("isDefault", defaultAddress.getIsDefault());
            result.put("addressInfo", addressInfo);
        }
        
        return AjaxResult.success(result);
    }

    @PostMapping("/cancel/{orderId}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult cancelOrder(@PathVariable String orderId) {
        // 1. 查询所有相关订单记录
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getOrderId, orderId)
        );
        
        if (orders.isEmpty()) {
            return AjaxResult.error("订单不存在");
        }
        
        // 2. 检查订单状态
        StoreOrder firstOrder = orders.get(0);
        if (firstOrder.getPaid() == 1) {
            return AjaxResult.error("订单已支付，无法取消");
        }
        
        if (firstOrder.getStatus() == 1) {
            return AjaxResult.error("订单已发货，无法取消");
        }
        
        // 3. 更新所有订单状态为3（已取消）
        LambdaUpdateWrapper<StoreOrder> updateWrapper = new LambdaUpdateWrapper<StoreOrder>()
            .eq(StoreOrder::getOrderId, orderId)
            .set(StoreOrder::getStatus, 3) // 设置为true表示已取消
            .set(StoreOrder::getUpdateTime, LocalDateTime.now());
            
        boolean result = storeOrderService.update(updateWrapper);
        if (!result) {
            return AjaxResult.error("取消订单失败");
        }
        
        return AjaxResult.success("取消订单成功");
    }

    @GetMapping("/user/{userId}")
    public AjaxResult getUserOrders(@PathVariable Integer userId) {
        // 1. 查询用户所有订单
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getUid, userId)
                .orderByDesc(StoreOrder::getCreateTime) // 按创建时间倒序排序
        );
        
        if (orders.isEmpty()) {
            return AjaxResult.success(new ArrayList<>()); // 返回空列表
        }
        
        // 2. 组装订单信息
        List<Map<String, Object>> orderList = new ArrayList<>();
        
        for (StoreOrder order : orders) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("orderId", order.getOrderId());
            orderMap.put("orderStatus", order.getStatus());
            orderMap.put("paid", order.getPaid());
            orderMap.put("totalPrice", order.getTotalPrice());
            orderMap.put("freightPrice", order.getFreightPrice());
            orderMap.put("totalAmount", order.getTotalPrice().add(order.getFreightPrice()));
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("payTime", order.getPayTime());
            
            // 获取商品信息
            StoreGood good = storeGoodService.getById(order.getGoodId());
            if (good != null) {
                Map<String, Object> goodInfo = new HashMap<>();
                goodInfo.put("goodId", good.getGoodId());
                goodInfo.put("goodName", good.getGoodName());
                goodInfo.put("goodImage", good.getImage());
                goodInfo.put("price", good.getPrice());
                goodInfo.put("quantity", order.getTotalNum());
                orderMap.put("goodInfo", goodInfo);
            }
            
            // 获取收货地址信息
            if (order.getAddressId() != null) {
                StoreAddress address = storeAddressService.getById(order.getAddressId());
                if (address != null) {
                    Map<String, Object> addressInfo = new HashMap<>();
                    addressInfo.put("name", address.getName());
                    addressInfo.put("phone", address.getTel());
                    addressInfo.put("addressDetail", address.getAddressDetail());
                    orderMap.put("addressInfo", addressInfo);
                }
            }
            
            orderList.add(orderMap);
        }
        
        return AjaxResult.success(orderList);
    }

    @PostMapping("/pay/{orderId}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult payOrder(@PathVariable String orderId) {
        // 1. 查询所有相关订单记录
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getOrderId, orderId)
        );
        
        if (orders.isEmpty()) {
            return AjaxResult.error("订单不存在");
        }
        
        // 2. 检查订单状态
        StoreOrder firstOrder = orders.get(0);
        if (firstOrder.getPaid() == 1) {
            return AjaxResult.error("订单已支付");
        }
        
        if (firstOrder.getStatus() == 2) {
            return AjaxResult.error("订单已取消，无法支付");
        }
        
        // 3. 更新所有订单的支付状态和支付时间
        LocalDateTime now = LocalDateTime.now();
        LambdaUpdateWrapper<StoreOrder> updateWrapper = new LambdaUpdateWrapper<StoreOrder>()
            .eq(StoreOrder::getOrderId, orderId)
            .set(StoreOrder::getPaid, 1) // 设置为已支付
            .set(StoreOrder::getStatus, 0) // 设置为0（待发货）
            .set(StoreOrder::getPayTime, now) // 设置支付时间
            .set(StoreOrder::getUpdateTime, now);
            
        boolean result = storeOrderService.update(updateWrapper);
        if (!result) {
            return AjaxResult.error("支付失败");
        }
        
        return AjaxResult.success("支付成功");
    }

    // 查询用户未处理订单
    @GetMapping("/unprocessed/{userId}")
    public AjaxResult getUnprocessedOrders(@PathVariable Integer userId) {
        // 1. 查询用户未处理订单
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .and(wrapper -> wrapper
                    .eq(StoreOrder::getUid, userId)
                    .or()
                    .eq(StoreOrder::getMerId, userId)
                )
                .and(wrapper -> wrapper
                    .eq(StoreOrder::getStatus, 0)  // status = 0
                    .or()
                    .eq(StoreOrder::getStatus, 1)   // status = 1
                )
                .orderByDesc(StoreOrder::getCreateTime)
        );
        
        if (orders.isEmpty()) {
            return AjaxResult.success(new ArrayList<>());
        }
        
        // 2. 组装订单信息
        List<Map<String, Object>> orderList = new ArrayList<>();
        
        for (StoreOrder order : orders) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("orderId", order.getOrderId());
            orderMap.put("orderStatus", order.getStatus());
            orderMap.put("paid", order.getPaid());
            orderMap.put("totalPrice", order.getTotalPrice());
            orderMap.put("freightPrice", order.getFreightPrice());
            orderMap.put("totalAmount", order.getTotalPrice().add(order.getFreightPrice()));
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("payTime", order.getPayTime());
            
            // 获取商品信息
            StoreGood good = storeGoodService.getById(order.getGoodId());
            if (good != null) {
                Map<String, Object> goodInfo = new HashMap<>();
                goodInfo.put("goodId", good.getGoodId());
                goodInfo.put("goodName", good.getGoodName());
                goodInfo.put("goodImage", good.getImage());
                goodInfo.put("price", good.getPrice());
                goodInfo.put("quantity", order.getTotalNum());
                orderMap.put("goodInfo", goodInfo);
            }
            
            // 获取收货地址信息
            if (order.getAddressId() != null) {
                StoreAddress address = storeAddressService.getById(order.getAddressId());
                if (address != null) {
                    Map<String, Object> addressInfo = new HashMap<>();
                    addressInfo.put("addressId", address.getAddressId());
                    addressInfo.put("name", address.getName());
                    addressInfo.put("phone", address.getTel());
                    addressInfo.put("addressDetail", address.getAddressDetail());
                    addressInfo.put("isDefault", address.getIsDefault());
                    
                    // 获取校区信息
                    if (address.getCampusId() != null) {
                        StoreCampus campus = storeCampusService.getById(address.getCampusId());
                        if (campus != null) {
                            addressInfo.put("campusId", campus.getCampusId());
                            addressInfo.put("campusName", campus.getCampusName());
                        }
                    }
                    
                    // 获取楼栋信息
                    if (address.getFloorId() != null) {
                        StoreFloor floor = storeFloorService.getById(address.getFloorId());
                        if (floor != null) {
                            addressInfo.put("floorId", floor.getFloorId());
                            addressInfo.put("floorName", floor.getFloorName());
                        }
                    }
                    
                    orderMap.put("addressInfo", addressInfo);
                }
            }
            
            // 获取买家信息
            StoreUser buyer = storeUserService.getById(order.getUid());
            if (buyer != null) {
                Map<String, Object> buyerInfo = new HashMap<>();
                buyerInfo.put("userId", buyer.getUserId());
                buyerInfo.put("username", buyer.getUsername());
                buyerInfo.put("phone", buyer.getMobile());
                orderMap.put("buyerInfo", buyerInfo);
            }
            
            // 获取卖家信息
            StoreUser seller = storeUserService.getById(order.getMerId());
            if (seller != null) {
                Map<String, Object> sellerInfo = new HashMap<>();
                sellerInfo.put("userId", seller.getUserId());
                sellerInfo.put("username", seller.getUsername());
                sellerInfo.put("phone", seller.getMobile());
                orderMap.put("sellerInfo", sellerInfo);
            }
            
            orderList.add(orderMap);
        }
        
        return AjaxResult.success(orderList);
    }

    // 查询用户已完成订单
    @GetMapping("/completed/{userId}")
    public AjaxResult getCompletedOrders(@PathVariable Integer userId) {
        // 1. 查询用户已完成订单
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .and(wrapper -> wrapper
                    .eq(StoreOrder::getUid, userId)
                    .or()
                    .eq(StoreOrder::getMerId, userId)
                )
                .and(wrapper -> wrapper
                    .eq(StoreOrder::getStatus, 2)  // status = 2（已完成）
                    .or()
                    .eq(StoreOrder::getStatus, 3)   // status = 3（已取消）
                )
                .orderByDesc(StoreOrder::getUpdateTime)
        );
        
        if (orders.isEmpty()) {
            return AjaxResult.success(new ArrayList<>());
        }
        
        // 2. 组装订单信息
        List<Map<String, Object>> orderList = new ArrayList<>();
        
        for (StoreOrder order : orders) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("orderId", order.getOrderId());
            orderMap.put("orderStatus", order.getStatus());
            orderMap.put("paid", order.getPaid());
            orderMap.put("totalPrice", order.getTotalPrice());
            orderMap.put("freightPrice", order.getFreightPrice());
            orderMap.put("totalAmount", order.getTotalPrice().add(order.getFreightPrice()));
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("payTime", order.getPayTime());
            orderMap.put("updateTime", order.getUpdateTime());
            
            // 获取商品信息
            StoreGood good = storeGoodService.getById(order.getGoodId());
            if (good != null) {
                Map<String, Object> goodInfo = new HashMap<>();
                goodInfo.put("goodId", good.getGoodId());
                goodInfo.put("goodName", good.getGoodName());
                goodInfo.put("goodImage", good.getImage());
                goodInfo.put("price", good.getPrice());
                goodInfo.put("quantity", order.getTotalNum());
                orderMap.put("goodInfo", goodInfo);
            }
            
            // 获取收货地址信息
            if (order.getAddressId() != null) {
                StoreAddress address = storeAddressService.getById(order.getAddressId());
                if (address != null) {
                    Map<String, Object> addressInfo = new HashMap<>();
                    addressInfo.put("addressId", address.getAddressId());
                    addressInfo.put("name", address.getName());
                    addressInfo.put("phone", address.getTel());
                    addressInfo.put("addressDetail", address.getAddressDetail());
                    addressInfo.put("isDefault", address.getIsDefault());
                    
                    // 获取校区信息
                    if (address.getCampusId() != null) {
                        StoreCampus campus = storeCampusService.getById(address.getCampusId());
                        if (campus != null) {
                            addressInfo.put("campusId", campus.getCampusId());
                            addressInfo.put("campusName", campus.getCampusName());
                        }
                    }
                    
                    // 获取楼栋信息
                    if (address.getFloorId() != null) {
                        StoreFloor floor = storeFloorService.getById(address.getFloorId());
                        if (floor != null) {
                            addressInfo.put("floorId", floor.getFloorId());
                            addressInfo.put("floorName", floor.getFloorName());
                        }
                    }
                    
                    orderMap.put("addressInfo", addressInfo);
                }
            }
            
            // 获取买家信息
            StoreUser buyer = storeUserService.getById(order.getUid());
            if (buyer != null) {
                Map<String, Object> buyerInfo = new HashMap<>();
                buyerInfo.put("userId", buyer.getUserId());
                buyerInfo.put("username", buyer.getUsername());
                buyerInfo.put("phone", buyer.getMobile());
                orderMap.put("buyerInfo", buyerInfo);
            }
            
            // 获取卖家信息
            StoreUser seller = storeUserService.getById(order.getMerId());
            if (seller != null) {
                Map<String, Object> sellerInfo = new HashMap<>();
                sellerInfo.put("userId", seller.getUserId());
                sellerInfo.put("username", seller.getUsername());
                sellerInfo.put("phone", seller.getMobile());
                orderMap.put("sellerInfo", sellerInfo);
            }
            
            orderList.add(orderMap);
        }
        
        return AjaxResult.success(orderList);
    }

    // 用户确认收货
    @PostMapping("/confirm")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult confirmOrder(@RequestBody Map<String, Object> params) {
        String orderId = (String) params.get("orderId");
        Integer userId = (Integer) params.get("userId");
        Integer goodId = (Integer) params.get("goodId");
        
        if (orderId == null || userId == null || goodId == null) {
            return AjaxResult.error("参数不完整");
        }
        
        // 1. 查询订单
        StoreOrder order = storeOrderService.getOne(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getOrderId, orderId)
                .eq(StoreOrder::getUid, userId)
                .eq(StoreOrder::getGoodId, goodId)
        );
        
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        
        // 2. 检查订单状态
        if (order.getStatus() == 2) {
            return AjaxResult.error("订单已完成");
        }
        if (order.getStatus() == 3) {
            return AjaxResult.error("订单已取消");
        }
        
        // 3. 更新订单状态为已完成
        LocalDateTime now = LocalDateTime.now();
        LambdaUpdateWrapper<StoreOrder> updateWrapper = new LambdaUpdateWrapper<StoreOrder>()
            .eq(StoreOrder::getOrderId, orderId)
            .eq(StoreOrder::getUid, userId)
            .eq(StoreOrder::getGoodId, goodId)
            .set(StoreOrder::getStatus, 2) // 设置为2（已完成）
            .set(StoreOrder::getUpdateTime, now);
            
        boolean result = storeOrderService.update(updateWrapper);
        if (!result) {
            return AjaxResult.error("确认收货失败");
        }
        
        return AjaxResult.success("确认收货成功");
    }

    // 商家发货
    @PostMapping("/ship/{orderId}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult shipOrder(@PathVariable String orderId, @RequestParam Integer userId) {
        // 1. 查询订单
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getOrderId, orderId)
        );
        
        if (orders.isEmpty()) {
            return AjaxResult.error("订单不存在");
        }
        
        // 2. 检查订单状态
        StoreOrder firstOrder = orders.get(0);
        if (firstOrder.getStatus() == 2) {
            return AjaxResult.error("订单已完成");
        }
        if (firstOrder.getStatus() == 3) {
            return AjaxResult.error("订单已取消");
        }
        if (firstOrder.getStatus() == 1) {
            return AjaxResult.error("订单已发货");
        }
        
        // 3. 验证商家身份
        if (!firstOrder.getMerId().equals(userId)) {
            return AjaxResult.error("无权操作此订单");
        }
        
        // 4. 更新订单状态为已发货
        LocalDateTime now = LocalDateTime.now();
        LambdaUpdateWrapper<StoreOrder> updateWrapper = new LambdaUpdateWrapper<StoreOrder>()
            .eq(StoreOrder::getOrderId, orderId)
            .set(StoreOrder::getStatus, 1) // 设置为1（已发货）
            .set(StoreOrder::getUpdateTime, now);
            
        boolean result = storeOrderService.update(updateWrapper);
        if (!result) {
            return AjaxResult.error("发货失败");
        }
        
        return AjaxResult.success("发货成功");
    }

    // 获取所有订单列表
    @GetMapping("/list")
    public AjaxResult getAllOrders() {
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getIsDel, 0)
                .orderByDesc(StoreOrder::getCreateTime)
        );
        
        if (orders.isEmpty()) {
            return AjaxResult.success(new ArrayList<>());
        }
        
        // 组装订单信息
        List<Map<String, Object>> orderList = new ArrayList<>();
        for (StoreOrder order : orders) {
            Map<String, Object> orderMap = new HashMap<>();
            // 基本订单信息
            orderMap.put("orderId", order.getOrderId());
            orderMap.put("orderStatus", order.getStatus());
            orderMap.put("paid", order.getPaid());
            orderMap.put("totalPrice", order.getTotalPrice());
            orderMap.put("freightPrice", order.getFreightPrice());
            orderMap.put("totalAmount", order.getTotalPrice().add(order.getFreightPrice()));
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("payTime", order.getPayTime());
            
            // 获取商品信息
            StoreGood good = storeGoodService.getById(order.getGoodId());
            if (good != null) {
                Map<String, Object> goodInfo = new HashMap<>();
                goodInfo.put("goodId", good.getGoodId());
                goodInfo.put("goodName", good.getGoodName());
                goodInfo.put("goodImage", good.getImage());
                goodInfo.put("price", good.getPrice());
                goodInfo.put("quantity", order.getTotalNum());
                orderMap.put("goodInfo", goodInfo);
            }
            
            // 获取买家信息
            StoreUser buyer = storeUserService.getById(order.getUid());
            if (buyer != null) {
                Map<String, Object> buyerInfo = new HashMap<>();
                buyerInfo.put("userId", buyer.getUserId());
                buyerInfo.put("username", buyer.getUsername());
                buyerInfo.put("phone", buyer.getMobile());
                orderMap.put("buyerInfo", buyerInfo);
            }
            
            // 获取卖家信息
            StoreUser seller = storeUserService.getById(order.getMerId());
            if (seller != null) {
                Map<String, Object> sellerInfo = new HashMap<>();
                sellerInfo.put("userId", seller.getUserId());
                sellerInfo.put("username", seller.getUsername());
                sellerInfo.put("phone", seller.getMobile());
                orderMap.put("sellerInfo", sellerInfo);
            }
            
            orderList.add(orderMap);
        }
        
        return AjaxResult.success(orderList);
    }
}
