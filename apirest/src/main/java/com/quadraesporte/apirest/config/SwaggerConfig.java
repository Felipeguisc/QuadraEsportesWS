package com.quadraesporte.apirest.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
		public Docket scheduleApi() {
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.quadraesporte.apirest"))
					.paths(PathSelectors.regex("/api.*"))
					.build()
					.apiInfo(metaInfo());
	}
	
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"QuadraEsporte API REST",
				"API REST de cadastro para uso da quadra de esportes.",
				"1.0",
				"Terms of Service",
				new Contact("Felipe Guimaraes", "felipe.gsc@aluno.ifsc.edu.br", null),
				"Apache License Version 2.0",
				"https://www.apache.org/license.html", new ArrayList<VendorExtension>()
				);
		return apiInfo;
	}
	
}
