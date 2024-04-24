package com.zichen.managersystem.config;

import com.zichen.managersystem.interceptor.RoleInterceptor;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    RoleInterceptor roleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleInterceptor)
                .excludePathPatterns("/error")
                .addPathPatterns("/**");
    }

}
