package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ershou.ershou.domain.po.StoreOrder;
import com.ershou.ershou.service.IStoreOrderService;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单统计 前端控制器
 */
@RestController
@RequestMapping("/store-order-stat")
public class StoreOrderStatController {

    @Autowired
    private IStoreOrderService storeOrderService;

    /**
     * 获取订单总体统计信息
     */
    @GetMapping("/overview")
    public AjaxResult getOrderOverview() {
        // 查询所有未删除的订单
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getIsDel, 0)
                .eq(StoreOrder::getIsSystemDel, 0)
        );

        Map<String, Object> result = new HashMap<>();

        // 1. 订单量统计
        result.put("totalOrders", orders.size());
        Map<Integer, Long> statusCount = orders.stream()
            .collect(Collectors.groupingBy(StoreOrder::getStatus, Collectors.counting()));
        result.put("waitingShipment", statusCount.getOrDefault(0, 0L)); // 待发货
        result.put("waitingReceive", statusCount.getOrDefault(1, 0L));  // 待收货
        result.put("completed", statusCount.getOrDefault(2, 0L));       // 已完成
        result.put("cancelled", statusCount.getOrDefault(3, 0L));       // 已取消

        // 2. 交易金额统计
        BigDecimal totalAmount = orders.stream()
            .map(StoreOrder::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalAmount", totalAmount);

        BigDecimal totalFreight = orders.stream()
            .map(StoreOrder::getFreightPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalFreight", totalFreight);

        // 3. 支付相关统计
        long paidOrders = orders.stream()
            .filter(order -> order.getPaid() == 1)
            .count();
        result.put("paidOrders", paidOrders);
        result.put("paymentRate", orders.isEmpty() ? 0 : (double) paidOrders / orders.size());

        Map<String, Long> payTypeCount = orders.stream()
            .filter(order -> order.getPayType() != null)
            .collect(Collectors.groupingBy(StoreOrder::getPayType, Collectors.counting()));
        result.put("payTypeDistribution", payTypeCount);

        return AjaxResult.success(result);
    }

    /**
     * 获取用户行为统计
     */
    @GetMapping("/user-behavior")
    public AjaxResult getUserBehavior() {
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getIsDel, 0)
                .eq(StoreOrder::getIsSystemDel, 0)
        );

        Map<String, Object> result = new HashMap<>();

        // 1. 用户下单统计
        Map<Integer, Long> userOrderCount = orders.stream()
            .collect(Collectors.groupingBy(StoreOrder::getUid, Collectors.counting()));
        result.put("totalBuyers", userOrderCount.size());
        result.put("averageOrdersPerBuyer", orders.isEmpty() ? 0 : (double) orders.size() / userOrderCount.size());

        // 2. 用户地址使用统计
        Map<Integer, Long> addressCount = orders.stream()
            .filter(order -> order.getAddressId() != null)
            .collect(Collectors.groupingBy(StoreOrder::getAddressId, Collectors.counting()));
        result.put("totalAddressesUsed", addressCount.size());

        return AjaxResult.success(result);
    }

    /**
     * 获取商家统计信息
     */
    @GetMapping("/merchant")
    public AjaxResult getMerchantStats() {
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getIsDel, 0)
                .eq(StoreOrder::getIsSystemDel, 0)
        );

        Map<String, Object> result = new HashMap<>();

        // 1. 商家订单统计
        Map<Integer, List<StoreOrder>> merchantOrders = orders.stream()
            .collect(Collectors.groupingBy(StoreOrder::getMerId));
        
        result.put("totalMerchants", merchantOrders.size());
        
        // 2. 商家销售额统计
        Map<Integer, BigDecimal> merchantSales = new HashMap<>();
        merchantOrders.forEach((merId, orderList) -> {
            BigDecimal total = orderList.stream()
                .map(StoreOrder::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            merchantSales.put(merId, total);
        });
        result.put("merchantSales", merchantSales);

        return AjaxResult.success(result);
    }

    /**
     * 获取时间维度统计
     * @param days 统计天数
     */
    @GetMapping("/time-analysis/{days}")
    public AjaxResult getTimeAnalysis(@PathVariable Integer days) {
        LocalDateTime startTime = LocalDateTime.now().minusDays(days);
        
        List<StoreOrder> orders = storeOrderService.list(
            new LambdaQueryWrapper<StoreOrder>()
                .eq(StoreOrder::getIsDel, 0)
                .eq(StoreOrder::getIsSystemDel, 0)
                .ge(StoreOrder::getCreateTime, startTime)
        );

        Map<String, Object> result = new HashMap<>();

        // 1. 按天统计订单量
        Map<String, Long> dailyOrders = orders.stream()
            .collect(Collectors.groupingBy(
                order -> order.getCreateTime().toLocalDate().toString(),
                Collectors.counting()
            ));
        result.put("dailyOrders", dailyOrders);

        // 2. 按天统计交易额
        Map<String, BigDecimal> dailyAmount = new HashMap<>();
        orders.forEach(order -> {
            String date = order.getCreateTime().toLocalDate().toString();
            dailyAmount.merge(date, order.getTotalPrice(), BigDecimal::add);
        });
        result.put("dailyAmount", dailyAmount);

        return AjaxResult.success(result);
    }
} 