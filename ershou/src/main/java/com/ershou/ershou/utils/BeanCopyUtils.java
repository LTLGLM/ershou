package com.ershou.ershou.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        try {
            // 创建目标对象实例
            T target = targetClass.getDeclaredConstructor().newInstance();
            // 复制属性
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("对象属性拷贝失败", e);
        }
    }

    public static <T> List<T> copyPropertiesList(List<?> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                // 对每个源对象进行属性复制
                .map(source -> copyProperties(source, targetClass))
                .collect(Collectors.toList());
    }
}
