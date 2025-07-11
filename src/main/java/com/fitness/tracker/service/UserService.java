package com.fitness.tracker.service;

import com.fitness.tracker.model.User; //imports the user
import com.fitness.tracker.repository.UserRepository; //imports the repository

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired 
	private UserRepository userRepository;
	
	//find user by username
	 public Optional<User> findByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }
	
	 //Save a new user (register user)
	 public User saveUser(User user)
	 {
		 return userRepository.save(user);
	 }
}
