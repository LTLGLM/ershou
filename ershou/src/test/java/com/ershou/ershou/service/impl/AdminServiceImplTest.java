package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.AdminDto;
import com.ershou.ershou.mapper.AdminMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminMapper adminMapper;
    @Test
    void testFindAll() {
        // Page<AdminDto> page = new Page<>(0, 1);
        // IPage<AdminDto> adminList = adminMapper.getAdminList(page);
        // System.out.println(adminList.getRecords());
    }
}