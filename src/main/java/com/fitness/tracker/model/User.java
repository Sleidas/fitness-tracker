package com.fitness.tracker.model;

import jakarta.persistence.*; // For JPA annotations so it can map to a database table
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity // Marks class as a database entity (table)
@Table(name = "users")

public class User {

	@Id // Marks this field as the primary key of the table
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Eg. AUTOINCREMENT
	private Long id; // Unique identifier for each user

	@NotBlank(message = "Username is required") // Makes sure username is not empty
	@Column(unique = true) // Enforces username must be unique in the database
	private String username;

	@NotBlank(message = "Email is required") // MAkes sure email is not empty
	@Email(message = "Invalid email format") // Checks if the email is valid format
	@Column(unique = true) // Email must be unique
	private String email;

	@NotBlank(message = "Password is required")
	private String password;

	// Empty constructor
	public User() {
	}

//Constructor to initialise attributes
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	// Getters and setters for all fields so we can access and update them

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
