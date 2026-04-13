package com.ershou.ershou.utils;

import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import java.lang.reflect.Field;
import java.util.Date;

public class FieldFiller {

    public static void addFillFields(Object target) {
        try {
            Class<?> clazz = target.getClass();
            Field createBy = getFieldFormClass(clazz, "createBy");
            if (createBy != null) {
                createBy.setAccessible(true);

                LoginUserVo loginUserVo = SecurityAdminUtils.getAdmin();
                Admin admin = loginUserVo.getAdmin();

                // 设置创建用户的ID
                createBy.set(target, admin.getAdminId());
            }

            Field createTime = getFieldFormClass(clazz, "createTime");
            if (createTime != null) {
                createTime.setAccessible(true);
                createTime.set(target, new Date());
            }


            Field addTime = getFieldFormClass(clazz, "addTime");
            if(addTime != null) {
                addTime.setAccessible(true);
                addTime.set(target, new Date());
            }

        } catch (IllegalAccessException e) {
            throw new GeneralBusinessException(e.getMessage());
        }
    }

    public static void updateFillFields(Object target) {
        try {
            Class<?> clazz = target.getClass();
            Field updateBy = getFieldFormClass(clazz, "updateBy");
            if (updateBy != null) {
                updateBy.setAccessible(true);

                LoginUserVo loginUserVo = SecurityAdminUtils.getAdmin();
                Admin admin = loginUserVo.getAdmin();

                // 设置更新用户的ID
                updateBy.set(target, admin.getAdminId());
            }


            Field updateTime = getFieldFormClass(clazz, "updateTime");
            if (updateTime != null) {
                updateTime.setAccessible(true);
                updateTime.set(target, new Date());
            }

        } catch (IllegalAccessException e) {
            throw new GeneralBusinessException(e.getMessage());
        }
    }

    // 递归往上查询是否有这个字段
    public static Field getFieldFormClass(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
        // throw new GeneralBusinessException("字段" + fieldName + "不存在");
    }
}
