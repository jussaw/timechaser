package com.timechaser.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.timechaser.entity.User;
import com.timechaser.security.UserRoles;

@Service
public class AuthorizationService {
	Logger logger = LoggerFactory.getLogger(AuthorizationService.class);
	
	private final UserService userService;
	
	public AuthorizationService(UserService userService) {
		this.userService = userService;
	}

	public boolean isAdminOrSelf(Long userId) {
		logger.info("isAdminOrSelf method called with userId: {}", userId);

        User user = getUser(userId);
        
        if(user == null) {
        	return false;
        }

        boolean isAdmin = user.getRoles()
        		.stream()
        		.anyMatch(role -> role.getName().equals(UserRoles.ADMIN));

        boolean isSelf = user.getId().equals(userId);

        return isAdmin || isSelf;
    }
	
	public boolean isSelf(Long userId) {
		logger.info("isSelf method called with userId: {}", userId);

        User user = getUser(userId);
        
        if(user == null) {
        	return false;
        }

        return user.getId().equals(userId);
    }
	
	private User getUser(Long userId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     
        String username = authentication.getName();

       return userService.findByUsername(username).orElse(null);
	}
}
