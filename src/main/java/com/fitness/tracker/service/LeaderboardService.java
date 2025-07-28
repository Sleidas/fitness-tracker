package com.fitness.tracker.service;

import com.fitness.tracker.dto.LeaderboardEntry;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutLog;
import com.fitness.tracker.repository.WorkoutLogRepository;
import com.fitness.tracker.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private final UserService userService;
    private final WorkoutLogRepository workoutLogRepository;

    public LeaderboardService(UserService userService, WorkoutLogRepository workoutLogRepository) {
        this.userService = userService;
        this.workoutLogRepository = workoutLogRepository;
    }

    public List<LeaderboardEntry> getLeaderboard(User currentUser, String sort) {
    	
    	List<User> friends = new ArrayList<>(userService.getFriends(currentUser.getUsername()));
    	friends.add(currentUser);

        List<LeaderboardEntry> leaderboard = friends.stream().map(friend -> {
            List<WorkoutLog> workouts = workoutLogRepository.findByUser(friend);

            double totalCalories = workouts.stream()
                .mapToDouble(WorkoutLog::getCaloriesBurned)
                .sum();

            int totalWorkouts = workouts.size();

            long activeDays = workouts.stream()
                .map(WorkoutLog::getWorkoutDate)
                .distinct()
                .count();

            return new LeaderboardEntry(
                friend.getUsername(), totalCalories, totalWorkouts, activeDays
            );
        }).collect(Collectors.toList());

        // Sort based on param (default to calories)
        Comparator<LeaderboardEntry> comparator;
        if (sort == null) sort = "calories";

        switch(sort.toLowerCase()) {
            case "workouts":
                comparator = Comparator.comparingInt(LeaderboardEntry::getTotalWorkouts).reversed();
                break;
            case "activedays":
                comparator = Comparator.comparingLong(LeaderboardEntry::getActiveDays).reversed();
                break;
            case "calories":
            default:
                comparator = Comparator.comparingDouble(LeaderboardEntry::getTotalCalories).reversed();
                break;
        }

        leaderboard.sort(comparator);
        return leaderboard;
    }
    }