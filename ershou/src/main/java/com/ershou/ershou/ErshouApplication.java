package com.ershou.ershou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("com.ershou")
@MapperScan("com.ershou.ershou.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)  // TODO开启授权注解功能
public class ErshouApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErshouApplication.class, args);
    }

}
