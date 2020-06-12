package net.atpco.detour.application;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import net.atpco.detour.controller.CommonController;
import net.atpco.detour.controller.DetourController;
import net.atpco.detour.service.DetourService;
import net.atpco.detour.service.SearchService;

@Configuration
@Import({SwaggerConfig.class, 
	DetourCustomExceptionHandler.class, MongoConfig.class})
public class DetourConfiguration {

	@Autowired
	private Environment env;
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		return builder
				.setConnectTimeout(300000)
				.setReadTimeout(300000)
				.build();
	}

	@Bean
	public FilterRegistrationBean<?> createCORSfilter() {
		FilterRegistrationBean<CorsFilter> frb = new FilterRegistrationBean<>();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedOrigin("*");
		source.registerCorsConfiguration("/**", configuration);

		frb.setFilter(new CorsFilter(source));
		frb.setOrder(0);
		return frb;
	}

	@Bean
	public CommonController controller() throws IOException {
		return new CommonController(searchService());
	}
	
	@Bean
	public DetourController detourController() throws IOException {
		return new DetourController(detourService());
	}
	
	
	@Bean
	public SearchService searchService() {
		return new SearchService();
	}
	
	@Bean
	public DetourService detourService() {
		return new DetourService();
	}

}
