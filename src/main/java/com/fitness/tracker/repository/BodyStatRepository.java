package com.fitness.tracker.repository;

import com.fitness.tracker.model.BodyStat;
import com.fitness.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyStatRepository extends JpaRepository<BodyStat, Long> {
    // Returns all BodyStat entries for a user
    List<BodyStat> findByUser(User user);
	
    List<BodyStat> findByUserOrderByDateDesc(User user);  // Gets all body stats of a user, most recent first
}