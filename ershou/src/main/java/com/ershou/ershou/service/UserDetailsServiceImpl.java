package com.ershou.ershou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.Menu;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.mapper.AdminMapper;
import com.ershou.ershou.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin user = adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(username != null,Admin::getUsername,username));

        if(Objects.isNull(user)){
            throw new GeneralBusinessException("用户不存在");
        }

        LoginUserVo loginUserVo = new LoginUserVo(user);

        // 获取权限
        List<Menu> perms = menuMapper.selectPermsByUserId(user.getAdminId());
        loginUserVo.setPermissions(perms);

        return loginUserVo;
    }
}
