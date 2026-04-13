package com.ershou.ershou.controller;

import com.ershou.ershou.domain.dto.AdminDto;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.Role;
import com.ershou.ershou.service.AdminService;
import com.ershou.ershou.utils.BeanCopyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void getAdminListTest() {
        List<Admin> list = adminService.list();
        List<AdminDto> adminDtos = BeanCopyUtils.copyPropertiesList(list, AdminDto.class);
        System.out.println(adminDtos);
    }

    @Test
    public void testUpdateAdmin(){
        AdminDto admin = new AdminDto();
        admin.setAdminId(22);
        admin.setUsername("234");
        admin.setPassword(passwordEncoder.encode("123456"));
        boolean b = adminService.updateById(admin);

    }
}