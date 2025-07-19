package com.fitness.tracker.controller;

/*This controller will;
 * Show the form to input height & weight
   Handle form submission and save to DB
   Display BMI results and historical data
 */

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
public class BodyStatController {

    @Autowired
    private BodyStatService bodyStatService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkoutLogService workoutLogService;

    @PostMapping("/bodystats")
    public String saveBodyStat(@Valid @ModelAttribute BodyStat bodyStat, BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (bindingResult.hasErrors()) {
            // Add bindingResult and bodyStat back to model (optional but good)
            model.addAttribute("org.springframework.validation.BindingResult.bodyStat", bindingResult);
            model.addAttribute("bodyStat", bodyStat);

            // Add username and related data to model
            Optional<User> userOpt = userService.findByUsername(userDetails.getUsername());
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                model.addAttribute("username", userDetails.getUsername());

                // Latest BodyStat for display if available
                BodyStat latestBodyStat = bodyStatService.getLatestBodyStat(user);
                if (latestBodyStat != null) {
                    model.addAttribute("latestBodyStat", latestBodyStat);
                }

                // Latest WorkoutLog for display if available
                WorkoutLog latestWorkout = workoutLogService.getLatestWorkoutLog(user);
                if (latestWorkout != null) {
                    model.addAttribute("latestWorkout", latestWorkout);
                }

                // Add workoutLog (needed for workout modal form)
                if (!model.containsAttribute("workoutLog")) {
                    model.addAttribute("workoutLog", new WorkoutLog());
                }
            }

            return "home"; // return back to home page with form errors and all needed data
        }

        Optional<User> userOpt = userService.findByUsername(userDetails.getUsername());
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error"; 
        }

        User user = userOpt.get();
        bodyStat.setUser(user);
        bodyStat.setDate(LocalDate.now());
        bodyStatService.saveBodyStat(bodyStat);

        return "redirect:/";
    }
}