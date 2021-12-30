
package com.app.emedi;

import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@ConditionalOnExpression(value = "${swagger.disable:false}")
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()//
				.apis(RequestHandlerSelectors.any()).paths(Predicates.not(PathSelectors.regex("/error"))).build()
				.apiInfo(metadata()).useDefaultResponseMessages(false);

	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder().title("hospital domine sample project").description(
				"This is a sample application about how add patients and retraive their data based on id")
				.license("softwareengineerprem@gamil.com")
				.licenseUrl("https://www.google.com")
				.version("1.0.0")//
				.contact(new Contact("prem", "https://www.google.com", "softwareengineerprem@gamil.com"))
				.build();
	}

}
