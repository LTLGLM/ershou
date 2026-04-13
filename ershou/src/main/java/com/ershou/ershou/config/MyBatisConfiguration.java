package com.ershou.ershou.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfiguration {
    /**
     * 分页插件
     */
    //mybatis-plus分页插件
    //详细文档：https://baomidou.com/guide/page.html#使用-starter
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        // 初始化核心插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 设置分页上限
        pageInterceptor.setMaxLimit(1000L);
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }
}
