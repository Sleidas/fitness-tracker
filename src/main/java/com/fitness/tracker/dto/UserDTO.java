 package com.fitness.tracker.dto;
//data transfer object for invite friends feature

import com.fitness.tracker.model.User;

public class UserDTO {

    private Long id;
    private String username;

    //empty constructor
    public UserDTO() {
    }

    // Static factory method to convert User entity to UserDTO
    public static UserDTO from(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
} 