package com.fitness.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutLog;
import com.fitness.tracker.repository.WorkoutLogRepository;

@Service
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;

    // Constructor injection
    public WorkoutLogService(WorkoutLogRepository workoutLogRepository) {
        this.workoutLogRepository = workoutLogRepository;
    }

    // Save a workout log
    public WorkoutLog saveWorkoutLog(WorkoutLog workoutLog) {
        return workoutLogRepository.save(workoutLog);
    }

    // Get all workout logs for a user
    public List<WorkoutLog> getWorkoutLogsByUser(User user) {
        return workoutLogRepository.findByUser(user);
    }

    // Get the most recent workout
    public WorkoutLog getLatestWorkoutLog(User user) {
        List<WorkoutLog> logs = workoutLogRepository.findByUserOrderByWorkoutDateDesc(user);
        if (logs.isEmpty()) return null;
        return logs.get(0);
    }

    //  Count total calories burned by a user
    public double getTotalCaloriesBurned(User user) {
        List<WorkoutLog> logs = workoutLogRepository.findByUser(user);
        
        // Converts the list of WorkoutLogs into a stream for processing
        // Extracts the calories burned from each log (using getter method)
        // Sums up all the calorie values and returns the total
        return logs.stream()
                   .mapToDouble(WorkoutLog::getCaloriesBurned)
                   .sum();
    }
}