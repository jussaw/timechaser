package com.timechaser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

import com.timechaser.entity.User;
import com.timechaser.repository.UserRepository;
import com.timechaser.security.MyUserDetails;

public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user!");
        }

        return new MyUserDetails(user);
    }
}
