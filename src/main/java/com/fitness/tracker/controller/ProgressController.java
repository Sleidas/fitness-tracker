package com.fitness.tracker.controller;

import com.fitness.tracker.model.BodyStat;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutLog;
import com.fitness.tracker.service.BodyStatService;
import com.fitness.tracker.service.UserService;
import com.fitness.tracker.service.WorkoutLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private BodyStatService bodyStatService;

    @Autowired
    private WorkoutLogService workoutLogService;

    @Autowired
    private UserService userService;

    // 1. BMI over time
    @GetMapping("/bmi")
    public List<Map<String, Object>> getBmiOverTime(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow();

        List<BodyStat> stats = bodyStatService.getAllBodyStats(user); 

        List<Map<String, Object>> data = new ArrayList<>();
        for (BodyStat stat : stats) {
            Map<String, Object> point = new HashMap<>();
            point.put("date", stat.getDate().toString());
            point.put("bmi", stat.getBmi());
            data.add(point);
        }
        return data;
    }

    // 2. Weight over time
    @GetMapping("/weight")
    public List<Map<String, Object>> getWeightOverTime(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow();

        List<BodyStat> stats = bodyStatService.getAllBodyStats(user);

        List<Map<String, Object>> data = new ArrayList<>();
        for (BodyStat stat : stats) {
            Map<String, Object> point = new HashMap<>();
            point.put("date", stat.getDate().toString());
            point.put("weight", stat.getWeightKg());
            data.add(point);
        }
        return data;
    }

    // 3. Calories burned per day
    @GetMapping("/calories")
    public List<Map<String, Object>> getCaloriesPerDay(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow();

        List<WorkoutLog> logs = workoutLogService.getAllWorkoutLogs(user);

        Map<LocalDate, Double> caloriesPerDay = new TreeMap<>();
        for (WorkoutLog log : logs) {
            LocalDate date = log.getWorkoutDate();
            caloriesPerDay.put(date,
                caloriesPerDay.getOrDefault(date, 0.0) + log.getCaloriesBurned());
        }

        List<Map<String, Object>> data = new ArrayList<>();
        for (Map.Entry<LocalDate, Double> entry : caloriesPerDay.entrySet()) {
            Map<String, Object> point = new HashMap<>();
            point.put("date", entry.getKey().toString());
            point.put("calories", entry.getValue());
            data.add(point);
        }

        return data;
    }
}