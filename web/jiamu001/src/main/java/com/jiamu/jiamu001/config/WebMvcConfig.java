package com.jiamu.jiamu001.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    @Value("${myUpload.absoluteImgPath}")
    String realImgPath;
    @Value("${myUpload.imgPath}")
    String UrlImgPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(UrlImgPath + "**").addResourceLocations("file:"+realImgPath);
        //registry.addResourceHandler(imgPath + "tea/**").addResourceLocations("file:"+absoluteImgPath+"tea/");
    }


//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//        registry.addViewController("/").setViewName("forward:/index");
//
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//        super.addViewControllers(registry);
//
//    }
//




}
