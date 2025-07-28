package com.fitness.tracker.service;

import com.fitness.tracker.dto.UserDTO;
import com.fitness.tracker.model.FriendInvite;
import com.fitness.tracker.model.User; //imports the user
import com.fitness.tracker.repository.FriendInviteRepository;
import com.fitness.tracker.repository.UserRepository; //imports the repository

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired 
	private UserRepository userRepository; //injects user
	
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;  // Injects password encoder
	 
	 @Autowired
	 private FriendInviteRepository friendInvite;
	
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
	    
	    
	    public List<UserDTO> searchUsers(String query) {
	        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
	        return users.stream()
	                    .map(UserDTO::from)
	                    .collect(Collectors.toList());
	    }
	    
	    public void sendInvite(String inviterUsername, Long invitedToId) {
	        User inviter = userRepository.findByUsername(inviterUsername)
	            .orElseThrow(() -> new RuntimeException("Inviter not found"));

	        User invitedTo = userRepository.findById(invitedToId)
	            .orElseThrow(() -> new RuntimeException("Invited user not found"));

	        // Check if already invited, already friends, etc. (optional)

	        FriendInvite invite = new FriendInvite();
	        invite.setInviter(inviter);
	        invite.setInvitedTo(invitedTo);
	        invite.setStatus("PENDING"); // 

	        friendInvite.save(invite);
	    
}
	    
	    public List<FriendInvite> getPendingInvites(String username) {
	        User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	        return friendInvite.findByInvitedToAndStatus(user, "PENDING");
	    }

	    public void respondToInvite(Long inviteId, String username, boolean accept) {
	        FriendInvite invite = friendInvite.findById(inviteId)
	            .orElseThrow(() -> new RuntimeException("Invite not found"));
	        
	        if (!invite.getInvitedTo().getUsername().equals(username)) {
	            throw new RuntimeException("Not authorized to respond to this invite");
	        }


	        if (accept) {
	            invite.setStatus("ACCEPTED");

	            User inviter = invite.getInviter();
	            User invitedTo = invite.getInvitedTo();
	            
	            if (inviter.getFriends() == null) inviter.setFriends(new ArrayList<>());
	            if (invitedTo.getFriends() == null) invitedTo.setFriends(new ArrayList<>());

	            // Add each other as friends if not already
	            if (!inviter.getFriends().contains(invitedTo)) {
	                inviter.getFriends().add(invitedTo);
	            }
	            if (!invitedTo.getFriends().contains(inviter)) {
	                invitedTo.getFriends().add(inviter);
	            }

	            userRepository.save(inviter);
	            userRepository.save(invitedTo);

	        } else {
	            invite.setStatus("REJECTED");
	        }

	        friendInvite.save(invite);
	    }
	    
	    public List<User> getFriends(String username) {
	        User user = userRepository.findByUsername(username)
	                                  .orElseThrow(() -> new RuntimeException("User not found"));
	        return user.getFriends(); // List<User>
	    }
}
