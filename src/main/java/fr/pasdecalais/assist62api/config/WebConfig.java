package fr.pasdecalais.assist62api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.cors.allowed-origins.dev}")
    private String[] allowedOriginsDev;

    @Value("${app.cors.allowed-origins.prod}")
    private String[] allowedOriginsProd;

    @Value("${app.branch.status}")
    private String branchStatus;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/images/**")
                .addResourceLocations("file:uploads/images/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOrigins = branchStatus.equals("dev") ? allowedOriginsDev : allowedOriginsProd;
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
