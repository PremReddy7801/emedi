package com.grokonez.jwtauthentication.restassured;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
@ExtendWith(SpringExtension.class)
//@PropertySource("classpath:src/test/resources/application-dev.properties") // tried for testng
@TestPropertySource("classpath:src/main/resources/application.properties")
public class PropertiesRestassured {
	@Value("${spring.datasource.url}")
	String jdbc;
	String baseUrl="http://localhost:9090/select-student-all";
	
	@Test         //properties are working for only junit
	public void getSutdents() {
//		RestAssured.given().get(baseUrl).then().statusCode(200);
//		System.out.println(jdbc);
		
	}
	
	

}
