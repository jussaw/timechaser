package com.timechaser.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.timechaser.entity.User;
import com.timechaser.repository.UserRepository;
import com.timechaser.security.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Could not find user!");
        }

        return new MyUserDetails(user.get());
    }
}
