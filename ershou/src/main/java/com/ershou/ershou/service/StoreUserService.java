package com.ershou.ershou.service;

import com.ershou.ershou.domain.po.StoreUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
public interface StoreUserService extends IService<StoreUser> {
    Map<String, Object> checkLogin(String code);
}
