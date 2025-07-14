package com.fitness.tracker.service;

import com.fitness.tracker.model.User; //imports the user
import com.fitness.tracker.repository.UserRepository; //imports the repository

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired 
	private UserRepository userRepository;
	
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;  // Injects password encoder
	
	//find user by username
	 public Optional<User> findByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }
	
	// Saves user with explicit duplicate username/email check
	    public User saveUser(User user) throws Exception {
	        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
	            throw new Exception("Username already exists");
	        }
	        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
	            throw new Exception("Email already exists");
	        }
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return userRepository.save(user);
	    }
}
