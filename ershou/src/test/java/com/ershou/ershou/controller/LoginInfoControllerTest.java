package com.ershou.ershou.controller;

import com.ershou.ershou.service.LoginInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginInfoControllerTest {

    @Autowired
    private LoginInfoService loginInfoService;

    @Test
    public void testInsertLoginInfo() {
        // loginInfoService.insertLoginInfo()
    }
}