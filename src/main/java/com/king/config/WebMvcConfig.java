package com.king.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/**
 * @author King
 * @version 1.0
 * @description web静态资源放行
 * @date 2024/10/21 15:47
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 设置静态资源映射
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("开始进行静态资源映射...");
        registry.addResourceHandler("/nettyChat/**").addResourceLocations("classpath:/chat/");
    }

}
