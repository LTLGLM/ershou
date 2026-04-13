package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ershou.ershou.domain.po.Menu;
import com.ershou.ershou.mapper.MenuMapper;
import com.ershou.ershou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectPermsByUserId(Integer adminId) {
        return menuMapper.selectPermsByUserId(adminId);
    }
}
