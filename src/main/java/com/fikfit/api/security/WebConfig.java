package com.fikfit.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtAuthInterceptor jwtAuthInterceptor;
    public WebConfig(JwtAuthInterceptor jwtAuthInterceptor) {
        this.jwtAuthInterceptor = jwtAuthInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
            .addPathPatterns("/api/v1/student/**", "/api/v1/users/**", "/api/v1/profile/**")
            .excludePathPatterns("/api/v1/auth/**");
        // Protege todos os outros endpoints da API
        registry.addInterceptor(jwtAuthInterceptor)
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns("/api/v1/auth/**", "/api/v1/student/**", "/api/v1/users/**", "/api/v1/profile/**");
    }
}
