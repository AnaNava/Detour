package net.atpco.detour.application;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket detourapi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("net.atpco.detaour.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Detour API", 
				"Ready to travel but overwhelmed by all the health, safety, and travel restrictions around the world? Don’t get lost, take a DETOUR and find your slice of paradise!", 
				"1.0.0",
				"This API returns all the information!", 
				new Contact("ATPCO", "ATPCO", "atpco@atpco.net"),
				"License of API", 
				"http://providealicenseurl.com", 
				Collections.emptyList());
	}
}
