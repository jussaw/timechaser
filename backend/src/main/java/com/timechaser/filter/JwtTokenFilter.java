package com.timechaser.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.timechaser.entity.User;
import com.timechaser.repository.UserRepository;
import com.timechaser.security.MyUserDetails;
import com.timechaser.util.JwtTokenUtil;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
		super();
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepository = userRepository;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
        	filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validateToken(token)) {
        	filterChain.doFilter(request, response);
            return;
        }

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
	}
}
