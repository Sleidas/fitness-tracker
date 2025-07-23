package com.fitness.tracker.repository;

import com.fitness.tracker.model.FriendInvite;
import com.fitness.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendInviteRepository extends JpaRepository<FriendInvite, Long> {

    Optional<FriendInvite> findByInviterAndInvitedTo(User inviter, User invitedTo);

    List<FriendInvite> findByInviter(User inviter);

    List<FriendInvite> findByInvitedTo(User invitedTo);
    
    List<FriendInvite> findByInvitedToAndStatus(User invitedTo, String status);
}