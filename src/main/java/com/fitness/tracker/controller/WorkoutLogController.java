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

import com.fitness.tracker.model.User;
import com.fitness.tracker.model.WorkoutLog;
import com.fitness.tracker.service.UserService;
import com.fitness.tracker.service.WorkoutLogService;

import jakarta.validation.Valid;

@Controller
public class WorkoutLogController {

    @Autowired
    private WorkoutLogService workoutLogService;

    @Autowired
    private UserService userService;

    // Show form to input a new WorkoutLog
    @GetMapping("/workoutlogs/new")
    public String showWorkoutLogForm(Model model) {
        model.addAttribute("workoutLog", new WorkoutLog());
        return "workoutlogs/form"; // Thymeleaf template for the workout log form
    }

    // Handle form submission to save WorkoutLog with validation
    @PostMapping("/workoutlogs")
    public String saveWorkoutLog(@Valid @ModelAttribute WorkoutLog workoutLog, BindingResult bindingResult,
                                 @AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (bindingResult.hasErrors()) {
            // Return the form with validation errors
            return "workoutlogs/form";
        }

        Optional<User> userOpt = userService.findByUsername(userDetails.getUsername());

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error"; // or wherever you want to show error page
        }

        User user = userOpt.get();
        workoutLog.setUser(user);
        workoutLog.setWorkoutDate(LocalDate.now());
        workoutLogService.saveWorkoutLog(workoutLog);

        return "redirect:/workoutlogs"; // Redirect to list or summary page
    }
}