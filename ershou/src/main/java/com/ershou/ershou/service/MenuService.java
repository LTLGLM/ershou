package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.po.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> selectPermsByUserId(Integer adminId);
}
