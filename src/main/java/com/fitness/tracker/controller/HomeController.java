package com.fitness.tracker.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fitness.tracker.model.BodyStat;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutLog;
import com.fitness.tracker.service.BodyStatService;
import com.fitness.tracker.service.UserService;
import com.fitness.tracker.service.WorkoutLogService;

@Controller
public class HomeController {

	
	@Autowired
	private UserService userService;
	
    @Autowired
    private BodyStatService bodyStatService;

    @Autowired
    private WorkoutLogService workoutLogService;
	
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Welcome to My Fitness Tracker");
        
        // Get logged-in user name from Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Usually returns username or email
        
        // Add username to model
        model.addAttribute("username", username);
        
     // Get User object by username
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
        
        
        // Now get latest BodyStat using User object
        BodyStat latestBodyStat = bodyStatService.getLatestBodyStat(user);
        if (latestBodyStat != null) {
            model.addAttribute("latestBodyStat", latestBodyStat);
        }
        
        // Get latest WorkoutLog using User object
        WorkoutLog latestWorkout = workoutLogService.getLatestWorkoutLog(user);
        if (latestWorkout != null) {
            model.addAttribute("latestWorkout", latestWorkout);
        }
        
        }
        
        if (!model.containsAttribute("workoutLog")) {
            model.addAttribute("workoutLog", new WorkoutLog());
        }
        return "home";
    }
    
}