package com.app.emedi;

import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EmediApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmediApplication.class, args);
	}
	 @Bean
	   public WebMvcConfigurer corsConfigurer() {
	      return new WebMvcConfigurerAdapter() {
	         @Override
	         public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("medi/patient").allowedOrigins("http://localhost:4200");
	         }
	      };
	   }
	 @Bean
	 public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
		 
	 }
	
}
