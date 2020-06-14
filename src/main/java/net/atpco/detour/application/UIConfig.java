package net.atpco.detour.application;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@SuppressWarnings({"rawtypes","unchecked"})
public class UIConfig implements WebMvcConfigurer {
	
	@Bean
    public FilterRegistrationBean createCORSfilter(){
        FilterRegistrationBean frb = new FilterRegistrationBean();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**",configuration);

        frb.setFilter(new CorsFilter(source));
        frb.setOrder(0);
        return frb;
    }
}
