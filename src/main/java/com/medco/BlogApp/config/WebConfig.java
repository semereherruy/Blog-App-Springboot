package com.medco.BlogApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")  // Frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow specific HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true);  // Allow credentials (cookies, authorization headers)
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         String profilePath = Paths.get("uploads/profile-pictures").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/profile-pictures/**")
                .addResourceLocations(profilePath);

        String absolutePath = Paths.get("uploads/post-images").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/post-images/**")
                .addResourceLocations(absolutePath);

    }
}
