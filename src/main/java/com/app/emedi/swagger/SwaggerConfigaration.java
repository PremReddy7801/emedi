//package com.app.emedi.swagger;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.google.common.base.Predicates;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfigaration {
//	 @Bean
//	public Docket configaration() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.groupName("public_api")
//				.apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any()).paths(Predicates.not(PathSelectors.regex("/error"))).build()apiInfo();
//		
//		
//	}
//public ApiInfo apiInfo() {
//	return null;
//	
//}
//}
