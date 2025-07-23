package com.fitness.tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FriendInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User inviter;

    @ManyToOne
    private User invitedTo;

    private String status = "PENDING";

    private LocalDateTime sentAt = LocalDateTime.now();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public User getInvitedTo() {
        return invitedTo;
    }

    public void setInvitedTo(User invitedTo) {
        this.invitedTo = invitedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}