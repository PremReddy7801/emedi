package com.app.emedi.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthonticationFilter  extends UsernamePasswordAuthenticationFilter{

	AuthenticationManager manager;
	
	public CustomAuthonticationFilter(AuthenticationManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username is :" + username + " and password is :" + password);
		System.out.println("CustomSuthonticationFilter class attemptAuthentication() ");
		UsernamePasswordAuthenticationToken authenticationToken 
				= new UsernamePasswordAuthenticationToken(username, password);
		return manager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secrate".getBytes()); 
		String accessToken = JWT
								.create()
								.withSubject(user.getUsername())
								.withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
								.withIssuer(request.getRequestURI().toString())
								.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
								.sign(algorithm);
		String refreshToken = JWT
				.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000))
				.withIssuer(request.getRequestURI().toString())
				.sign(algorithm);
		
		 response.setHeader("accesstoken", accessToken);
		  response.setHeader("refreshtoken", refreshToken);
		  
		System.out.println("accessToken :"+accessToken);
		Map<String, String> responseToken = new HashMap<>();
		responseToken.put("access token", accessToken);
		responseToken.put("refresh token", refreshToken);
		
		new ObjectMapper().writeValue(response.getOutputStream(),responseToken);
		
	}

	
}
