package com.ershou.ershou.domain.vo;

import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.Menu;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoginUserVo implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String Token;
    private Admin admin;
    // 页面和按钮权限
    private List<Menu> permissions;

    public LoginUserVo(Admin admin) {
        this.admin = admin;
    }

    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }

        authorities = permissions.stream().map(item->new SimpleGrantedAuthority(item.getPerms())).collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
