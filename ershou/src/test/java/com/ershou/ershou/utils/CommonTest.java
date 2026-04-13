package com.ershou.ershou.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommonTest {

    @Test
    public void testsplit(){
        String path = "/user/main";
        String[] split = path.split("/");
        System.out.println(split.length);
    }
}
