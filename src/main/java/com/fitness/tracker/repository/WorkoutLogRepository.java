package com.fitness.tracker.repository;

import com.fitness.tracker.model.WorkoutLog;
import com.fitness.tracker.model.BodyStat;
import com.fitness.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
	
    List<WorkoutLog> findByUser(User user);
    
    List<WorkoutLog> findByUserOrderByWorkoutDateDesc(User user);  // Gets all workout logs for a user, recent first
    
    List<WorkoutLog> findByUserOrderByWorkoutDateAsc(User user);
}