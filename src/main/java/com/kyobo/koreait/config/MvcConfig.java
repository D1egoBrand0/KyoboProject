//package com.kyobo.koreait.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class MvcConfig implements WebMvcConfigurer {
//
//    String sysPath = "C:\\Users\\Administrator\\IdeaProjects\\새프로젝트\\upload savepoint\\";
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//        registry.addResourceHandler("/upload savepoint/**").addResourceLocations(sysPath);
//                                                // 요경로에 있는 것들은 sysPath에서 찾겠다.
////        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }
//}

//이건 확인하는용으로만 사용