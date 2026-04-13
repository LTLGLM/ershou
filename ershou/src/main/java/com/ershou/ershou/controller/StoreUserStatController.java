package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ershou.ershou.domain.po.StoreUser;
import com.ershou.ershou.service.StoreUserService;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.ZoneId;

/**
 * 用户统计 前端控制器
 */
@RestController
@RequestMapping("/store-user-stat")
public class StoreUserStatController {

    @Autowired
    private StoreUserService storeUserService;

    /**
     * 获取用户基础统计信息
     */
    @GetMapping("/basic")
    public AjaxResult getBasicStats() {
        List<StoreUser> users = storeUserService.list(
            new LambdaQueryWrapper<StoreUser>()
                .eq(StoreUser::getDeleted, 0)
        );

        Map<String, Object> result = new HashMap<>();
        
        // 1. 用户总数
        result.put("totalUsers", users.size());

        // 2. 性别分布
        Map<String, Long> genderStats = users.stream()
            .collect(Collectors.groupingBy(user -> {
                Integer gender = user.getGender();
                if (gender == null || gender == 0) return "未知";
                return gender == 1 ? "男" : "女";
            }, Collectors.counting()));
        result.put("genderDistribution", genderStats);

        return AjaxResult.success(result);
    }

    /**
     * 获取用户年龄分布
     */
    @GetMapping("/age")
    public AjaxResult getAgeDistribution() {
        List<StoreUser> users = storeUserService.list(
            new LambdaQueryWrapper<StoreUser>()
                .eq(StoreUser::getDeleted, 0)
                .isNotNull(StoreUser::getBirthday)
        );

        Map<String, Object> result = new HashMap<>();

        // 年龄段分布
        Map<String, Long> ageDistribution = users.stream()
            .collect(Collectors.groupingBy(user -> {
                LocalDate birthday = user.getBirthday();
                long age = ChronoUnit.YEARS.between(birthday, LocalDate.now());
                if (age < 18) return "18岁以下";
                else if (age < 25) return "18-24岁";
                else if (age < 35) return "25-34岁";
                else if (age < 45) return "35-44岁";
                else return "45岁以上";
            }, Collectors.counting()));

        result.put("ageDistribution", ageDistribution);

        // 计算平均年龄
        double averageAge = users.stream()
            .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
            .average()
            .orElse(0);
        result.put("averageAge", String.format("%.1f", averageAge));

        return AjaxResult.success(result);
    }

    /**
     * 获取用户注册趋势
     */
    @GetMapping("/registration")
    public AjaxResult getRegistrationTrend() {
        List<StoreUser> users = storeUserService.list(
            new LambdaQueryWrapper<StoreUser>()
                .eq(StoreUser::getDeleted, 0)
        );

        Map<String, Object> result = new HashMap<>();

        // 1. 按月统计注册用户数
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        Map<String, Long> monthlyRegistration = users.stream()
            .collect(Collectors.groupingBy(
                user -> {
                    // 将 Date 转换为 LocalDateTime
                    LocalDateTime localDateTime = user.getCreateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                    return localDateTime.format(monthFormatter);
                },
                Collectors.counting()
            ));
        result.put("monthlyRegistration", monthlyRegistration);

        // 2. 最近7天注册用户数
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Long> recentRegistration = users.stream()
            .filter(user -> {
                LocalDateTime localDateTime = user.getCreateTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
                return localDateTime.isAfter(sevenDaysAgo);
            })
            .collect(Collectors.groupingBy(
                user -> {
                    LocalDateTime localDateTime = user.getCreateTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                    return localDateTime.format(dayFormatter);
                },
                Collectors.counting()
            ));
        result.put("recentRegistration", recentRegistration);

        // 3. 计算增长率
        long totalUsers = users.size();
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        long lastMonthUsers = users.stream()
            .filter(user -> {
                LocalDateTime localDateTime = user.getCreateTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
                return localDateTime.isBefore(oneMonthAgo);
            })
            .count();
        double growthRate = lastMonthUsers == 0 ? 0 : 
            ((double)(totalUsers - lastMonthUsers) / lastMonthUsers) * 100;
        result.put("monthlyGrowthRate", String.format("%.2f%%", growthRate));

        return AjaxResult.success(result);
    }

    /**
     * 获取用户画像分析（性别和年龄的交叉分析）
     */
    @GetMapping("/portrait")
    public AjaxResult getUserPortrait() {
        List<StoreUser> users = storeUserService.list(
            new LambdaQueryWrapper<StoreUser>()
                .eq(StoreUser::getDeleted, 0)
                .isNotNull(StoreUser::getBirthday)
        );

        // 性别和年龄的交叉分析
        Map<String, Map<String, Long>> genderAgeDistribution = users.stream()
            .collect(Collectors.groupingBy(
                user -> {
                    Integer gender = user.getGender();
                    if (gender == null || gender == 0) return "未知";
                    return gender == 1 ? "男" : "女";
                },
                Collectors.groupingBy(user -> {
                    long age = ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now());
                    if (age < 18) return "18岁以下";
                    else if (age < 25) return "18-24岁";
                    else if (age < 35) return "25-34岁";
                    else if (age < 45) return "35-44岁";
                    else return "45岁以上";
                }, Collectors.counting())
            ));

        return AjaxResult.success(genderAgeDistribution);
    }
} 