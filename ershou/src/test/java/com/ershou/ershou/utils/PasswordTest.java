package com.ershou.ershou.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class PasswordTest {

    @Test
    public void testPassword(){
        String password = "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        System.out.println(encode);
        boolean matches = passwordEncoder.matches(password, "$2a$10$QOZOPyOPzzYaCX8TuM4Sx.XtwYTg/U.0mUdEIH5/b0pPfwyoF60W.");
        System.out.println(matches);
    }
}
