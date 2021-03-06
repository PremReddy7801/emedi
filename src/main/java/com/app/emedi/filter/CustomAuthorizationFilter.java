package com.app.emedi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		    response.setHeader("Access-Control-Allow-Credentials", "true");
		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		    response.setHeader("Access-Control-Max-Age", "3600");
		    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		if (request.getServletPath().equalsIgnoreCase("/emedi/frontdesk/login")) {
			System.out.println("if CustomAuthorisationFilter class doFilterInternal() ");
			filterChain.doFilter(request, response);
		} else {
			System.out.println("else CustomAuthorisationFilter class doFilterInternal() ");
			System.out.println("request :"+request.getHeader(HttpHeaders.AUTHORIZATION));
			String authorisationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			System.out.println("1111111");
			if(authorisationHeader!=null&&authorisationHeader.startsWith("Bearer ")) {
				try {
					System.out.println("2222222");
					String token =authorisationHeader.substring("Bearer ".length());
					System.out.println("token :"+token);
					Algorithm algorithm = Algorithm.HMAC256("secrate".getBytes()); 
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT =verifier.verify(token);
					String username =decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
					Stream.of(roles).forEach(role->authorities.add(new SimpleGrantedAuthority(role)));
					UsernamePasswordAuthenticationToken authenticationToken
							= new UsernamePasswordAuthenticationToken(username,null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					response.sendError(HttpStatus.FORBIDDEN.value());
					response.setStatus(HttpStatus.FORBIDDEN.value());
					Map<String, String> responceTokens= new HashMap<>();
					responceTokens.put("error :", e.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(),responceTokens );
				}
				
			}else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
