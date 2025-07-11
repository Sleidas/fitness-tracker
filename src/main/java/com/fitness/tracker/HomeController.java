package com.fitness.tracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Welcome to My Fitness Tracker");
        return "home";
    }
    
    @GetMapping("/goals")
    public String goals() {
        return "goals"; // corresponds to goals.html
    }
}