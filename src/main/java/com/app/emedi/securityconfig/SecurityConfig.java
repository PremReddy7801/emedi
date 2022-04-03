package com.app.emedi.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.emedi.filter.CustomAuthonticationFilter;
import com.app.emedi.filter.CustomAuthorizationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder encoder;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("SecurityConfig class configure()");
		CustomAuthonticationFilter authonticationFilter =new CustomAuthonticationFilter(authenticationManager());
		authonticationFilter.setFilterProcessesUrl("/emedi/frontdesk/login");
		http.cors().and()
			.csrf()
			.disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		    	.and()
				.authorizeRequests()
//				.anyRequest()
				.antMatchers("/emedi/frontdesk/**")
				.permitAll()
				.and()
				.authorizeRequests()
				.antMatchers("/medi/patient/**").hasAnyAuthority("ROLE_USER")
				.anyRequest()
//				.permitAll();
				.authenticated()
				.and()
				.addFilter(authonticationFilter)
				.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
				
			
	}
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	

}
