package com.fitness.tracker.controller;


import com.fitness.tracker.model.User;
import com.fitness.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class AuthController {

	@Autowired 
	private UserService userService; //uses services methods
	
	 // Shows the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Passes a blank User object to the view
        return "register"; // Returns register.html view
    }
    
    //handling registration form
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.saveUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
	
    // Shows the login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Returns login.html view
    }
}
