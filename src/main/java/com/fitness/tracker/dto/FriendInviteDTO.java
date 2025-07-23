package com.fitness.tracker.dto;

import com.fitness.tracker.model.FriendInvite;

public class FriendInviteDTO {
    private Long id;
    private String inviterUsername;
    private String status;

    public FriendInviteDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInviterUsername() {
        return inviterUsername;
    }

    public void setInviterUsername(String inviterUsername) {
        this.inviterUsername = inviterUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static FriendInviteDTO fromEntity(FriendInvite invite) {
        FriendInviteDTO dto = new FriendInviteDTO();
        dto.setId(invite.getId());
        dto.setInviterUsername(invite.getInviter().getUsername());
        dto.setStatus(invite.getStatus());
        return dto;
    }
}