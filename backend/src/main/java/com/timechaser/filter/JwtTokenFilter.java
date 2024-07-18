package com.timechaser.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.entity.User;
import com.timechaser.exception.ErrorResponse;
import com.timechaser.repository.UserRepository;
import com.timechaser.security.MyUserDetails;
import com.timechaser.util.JwtTokenUtil;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	private final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	
	private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserRepository userRepository, ObjectMapper objectMapper) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
			
	        if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
	        	filterChain.doFilter(request, response);
	            return;
	        }
	
	        // Get jwt token and validate
	        final String token = header.split(" ")[1].trim();
	        
	        jwtTokenUtil.validateToken(token);
	        
	        // Get user identity and set it on the spring security context
	    	UserDetails userDetails = null;
	
	        User user = userRepository
	            .findByUsername(jwtTokenUtil.getUsernameFromToken(token))
	            .orElse(null);
	        if(user != null) {
	        	userDetails = new MyUserDetails(user);
	        }
	
	        UsernamePasswordAuthenticationToken
	            authentication = new UsernamePasswordAuthenticationToken(
	            	userDetails, 
	            	null,
	                userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
	            );
	
	        authentication.setDetails(
	            new WebAuthenticationDetailsSource().buildDetails(request)
	        );
	
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        filterChain.doFilter(request, response);


		} catch (JWTVerificationException e) {
			logger.error("An validating JWT Token has occurred", e);
			
			ErrorResponse errorResponse = new ErrorResponse(
					LocalDateTime.now(), 
					HttpStatus.FORBIDDEN.value(), 
					e.getMessage(), 
					request.getRequestURI(),
					request.getMethod());
			
            response.setContentType("application/json");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		}
	}
}
