package com.fitness.tracker.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitness.tracker.dto.LeaderboardEntry;
import com.fitness.tracker.model.User;
import com.fitness.tracker.service.LeaderboardService;
import com.fitness.tracker.service.UserService;

@Controller
public class LeaderboardController {

    private final LeaderboardService leaderboardService;
    private final UserService userService;

    public LeaderboardController(LeaderboardService leaderboardService, UserService userService) {
        this.leaderboardService = leaderboardService;
        this.userService = userService;
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(
            @RequestParam(name = "sort", required = false, defaultValue = "calories") String sort,
            Model model, 
            Principal principal) {
        
        String username = principal.getName();
        User currentUser = userService.findByUsername(username)
                              .orElseThrow(() -> new RuntimeException("User not found"));

        // Pass the sort param to the service
        List<LeaderboardEntry> leaderboard = leaderboardService.getLeaderboard(currentUser, sort);

        model.addAttribute("leaderboard", leaderboard);
        model.addAttribute("currentUsername", currentUser.getUsername());
        model.addAttribute("currentSort", sort);  // optionally to highlight current sort link in UI

        return "leaderboard";  
    }
}