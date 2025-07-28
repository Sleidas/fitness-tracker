package com.fitness.tracker.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fitness.tracker.model.BodyStat;
import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutLog;
import com.fitness.tracker.service.BodyStatService;
import com.fitness.tracker.service.UserService;
import com.fitness.tracker.service.WorkoutLogService;

import jakarta.validation.Valid;

@Controller
public class WorkoutLogController {

    @Autowired
    private WorkoutLogService workoutLogService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private BodyStatService bodyStatService;

    // Handle form submission to save WorkoutLog with validation
    @PostMapping("/workoutlogs")
    public String saveWorkoutLog(@Valid @ModelAttribute WorkoutLog workoutLog, BindingResult bindingResult,
                                 @AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.workoutLog", bindingResult);
            model.addAttribute("workoutLog", workoutLog);

            Optional<User> userOpt = userService.findByUsername(userDetails.getUsername());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                populateHomeModel(user, model);
            }

            return "home";
        }

        Optional<User> userOpt = userService.findByUsername(userDetails.getUsername());

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error"; 
        }

        User user = userOpt.get();
        workoutLog.setUser(user);
        workoutLogService.saveWorkoutLog(workoutLog);

        return "redirect:/";
    }

    private void populateHomeModel(User user, Model model) {
        model.addAttribute("username", user.getUsername());

        BodyStat latestBodyStat = bodyStatService.getLatestBodyStat(user);
        if (latestBodyStat != null) {
            model.addAttribute("latestBodyStat", latestBodyStat);
        }

        WorkoutLog latestWorkout = workoutLogService.getLatestWorkoutLog(user);
        if (latestWorkout != null) {
            model.addAttribute("latestWorkout", latestWorkout);
        }

        if (!model.containsAttribute("workoutLog")) {
            model.addAttribute("workoutLog", new WorkoutLog());
        }

        if (!model.containsAttribute("bodyStat")) {
            model.addAttribute("bodyStat", new BodyStat());
        }
    }
}