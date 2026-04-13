package com.ershou.ershou.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${SHTP.image_upload_path}")
    private String basePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //        设置静态资源映射（WebMvcConfig类中的addResourceHandlers方法），否则接口文档页面无法访问
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        // 配置静态资源映射，意思是 当访问路径为 /images 的就会去访问这个文件夹下的文件，
        // 比如 http://127.0.0.1:8888/images/717dec8a-2088-43dc-b180-7dd1fa8cac66.jpeg
        //  可以任意更改 /images ，比如 /img 都可以 ，但是 访问的时候 路径要变成的改成
        //  http://127.0.0.1:8888/img/717dec8a-2088-43dc-b180-7dd1fa8cac66.jpeg
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + basePath);
    }
}
