package com.kimseongje.springbootsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    // 단순 맵핑
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/my").setViewName("my");
//    }
}
