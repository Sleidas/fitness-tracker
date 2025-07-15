package com.fitness.tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				// Allow anyone to access the home page, login, register, and static resources
				.requestMatchers("/", "/login", "/register", "/images/**", "/css/**", "/h2-console/**").permitAll()  //css so styles load in even before log in
				// All other requests need authentication
				.anyRequest().authenticated()).formLogin(form -> form
						// login page URL
						.loginPage("/login")
						// Redirects to home page after successful login
						.defaultSuccessUrl("/", true).permitAll())
				.logout(logout -> logout
						// Redirect to login page with logout message after logging out
						.logoutSuccessUrl("/login?logout").permitAll());


	    // Disabled CSRF protection for H2 console to work properly
	    http.csrf(csrf -> csrf.disable());
	    // Disabled frame options to allow H2 console to display in a frame
	    http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
		
		return http.build();
	}
	
	//bean to handle password encryption , strong hashing algorithm
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
}
