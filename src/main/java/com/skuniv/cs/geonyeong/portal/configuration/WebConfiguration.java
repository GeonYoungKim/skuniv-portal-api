package com.skuniv.cs.geonyeong.portal.configuration;

import com.skuniv.cs.geonyeong.portal.interceptor.PortalInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final PortalInterceptor portalInterceptor;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("skuniv portal")
            .select()
            .apis(RequestHandlerSelectors
                .basePackage("com.skuniv.cs.geonyeong.portal.controller"))
            .paths(PathSelectors.ant("/api/v1/portal/**"))
            .build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(portalInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/api/v1/portal/account/**")
        ;
    }
}
