package com.hcn.hcn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // C:/uploads/에 파일을 저장한다고 가정하며
        // 이 파일들은 http://<server>:<port>/images/<filename>을 통해 접근 가능
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/uploads/");
    }
}
