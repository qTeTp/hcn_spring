package com.hcn.hcn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    private boolean cacheEnabled = true;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://10.20.66.184:8080", "http://10.20.66.184:19000", "http://10.20.66.184:8081")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true);  // 자격 증명 허용
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath)
                .setCachePeriod(cacheEnabled ? 3600 * 24 : 0); // 캐시 설정
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearCache() {
        cacheEnabled = false;
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void enableCache() {
        cacheEnabled = true;
    }
}
