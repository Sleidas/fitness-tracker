package com.fitness.tracker.config;

import com.fitness.tracker.model.User;
import com.fitness.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
public class CustomUsersDetailsService implements UserDetailsService { //userDetailService is used to load users from the database

    @Autowired
    private UserRepository userRepository;

    // Tells Spring Security how to load a user from your DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
    	User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.emptyList() // No roles yet
        );
    }
}