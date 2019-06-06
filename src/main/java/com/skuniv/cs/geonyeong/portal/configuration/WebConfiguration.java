package com.skuniv.cs.geonyeong.portal.configuration;

import com.skuniv.cs.geonyeong.portal.filter.PortalAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration {
    private final PortalAuthenticationFilter portalAuthenticationFilter;
    private final String[] URL_FILTER_PATTERNS = {

    };

    @Bean
    public FilterRegistrationBean<PortalAuthenticationFilter> authenticationFilter(){
        FilterRegistrationBean<PortalAuthenticationFilter> registrationBean
            = new FilterRegistrationBean<>();

        registrationBean.setFilter(portalAuthenticationFilter);
        registrationBean.addUrlPatterns(URL_FILTER_PATTERNS);
        return registrationBean;
    }
}
