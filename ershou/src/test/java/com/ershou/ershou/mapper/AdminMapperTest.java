package com.ershou.ershou.mapper;

import com.ershou.ershou.domain.po.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdminMapperTest {

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void getAdmin(){
        List<Admin> userList = adminMapper.selectList(null);
        for (Admin admin : userList) {
            System.out.println(admin);
        }
    }
}
