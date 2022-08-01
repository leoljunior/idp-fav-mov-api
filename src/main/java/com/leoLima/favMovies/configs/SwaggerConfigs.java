package com.leoLima.favMovies.configs;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigs {

	final String apiVersion = "1.0.0";
	final String apiTitle = "My Favorites Movies";
	final String apiDescription = "API that allows the customer to create a list of favorite movies using the IMDb API(Internet Movie Database)";

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.leoLima.favMovies"))
				.paths(PathSelectors.any())				
				.build()
				.useDefaultResponseMessages(false)
				.ignoredParameterTypes(BasicErrorController.class)
				.apiInfo(new ApiInfoBuilder()
						.version(apiVersion)
						.title(apiTitle)
						.description(apiDescription)
						.build());
	}
	
}
