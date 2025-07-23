package com.fitness.tracker.controller;

import com.fitness.tracker.dto.FriendInviteDTO;
import com.fitness.tracker.dto.InviteRequest;
import com.fitness.tracker.dto.UserDTO;
import com.fitness.tracker.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    ///GET /api/users/search?q=searchTerm
    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam("q") String query) {
     return userService.searchUsers(query);
    }
    
    //for inviting
    @PostMapping("/invite")
    public ResponseEntity<String> sendInvite(@RequestBody InviteRequest request, Principal principal) {
        String inviterUsername = principal.getName(); // Spring Security
        userService.sendInvite(inviterUsername, request.getInvitedUserId());
        return ResponseEntity.ok("Invite sent");
    }
    
    @GetMapping("/invites")
    public List<FriendInviteDTO> getPendingInvites(Principal principal) {
        return userService.getPendingInvites(principal.getName())
                .stream()
                .map(FriendInviteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //respond to an invite (accept or reject)
    @PostMapping("/invites/{id}/respond")
    public ResponseEntity<String> respondToInvite(@PathVariable Long id, @RequestParam boolean accept, Principal principal) {
        String username = principal.getName();
        userService.respondToInvite(id, username, accept);
        return ResponseEntity.ok("Invite " + (accept ? "accepted" : "rejected"));
    }
    
    @GetMapping("/friends")
    public List<UserDTO> getFriends(Principal principal) {
        return userService.getFriends(principal.getName())
                          .stream()
                          .map(UserDTO::from)
                          .collect(Collectors.toList());
    }
}