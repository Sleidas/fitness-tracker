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
import com.fitness.tracker.service.BodyStatService;
import com.fitness.tracker.service.UserService;

import jakarta.validation.Valid;

@Controller
public class BodyStatController {

    @Autowired
    private BodyStatService bodyStatService;

    @Autowired
    private UserService userService;

    // Shows form to input new BodyStat
    @GetMapping("/bodystats/new")
    public String showBodyStatForm(Model model) {
        model.addAttribute("bodyStat", new BodyStat());
        return "bodystats/form"; // Thymeleaf template for form
    }

    // Handle form submission to save BodyStat with validation
    @PostMapping("/bodystats")
    public String saveBodyStat(@Valid @ModelAttribute BodyStat bodyStat, BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails, Model model) { //binding result is validation report for form
        if (bindingResult.hasErrors()) {
            // Return form with validation errors
            return "bodystats/form";
        }

        Optional<User> userOpt = userService.findByUsername(userDetails.getUsername()); //checks if logged in user is in db

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error"; 
        }

        User user = userOpt.get();
        bodyStat.setUser(user);
        bodyStat.setDate(LocalDate.now());
        bodyStatService.saveBodyStat(bodyStat);

        return "redirect:/bodystats";
    }
}
