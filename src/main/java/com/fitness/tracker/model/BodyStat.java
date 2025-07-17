package com.fitness.tracker.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "body_stats")
public class BodyStat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
	private Long id;

	@NotNull
	private double heightCm; // height in cm

	@NotNull
	private double weightKg; // weight in kg

	private double bmi; // calculated after input
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user; // linking user to the entries

	// empty constructor required by JPA
	public BodyStat() {
	}

	public BodyStat(double heightCm, double weightKg, LocalDate date, User user) {
		this.heightCm = heightCm;
		this.weightKg = weightKg;
		this.date = date;
		this.user = user;
		this.bmi = calculateBmi(); // Auto-calculate on entry
	}

	private double calculateBmi() {
		// TODO Auto-generated method stub
		// BMI = weight(kg)/height(m)^2
		double heightMetres = heightCm / 100;

		return weightKg / (heightMetres * heightMetres);
	}

	public String calculateBmiCategory(double bmi) {
		if (bmi < 18.5)
			return "Underweight";
		else if (bmi < 25.0)
			return "Normal weight";
		else if (bmi < 30.0)
			return "Overweight";
		else
			return "Obese";
	}

	@PrePersist // runs before entity is inserted into database
	@PreUpdate // runs before entity is updated into database
	public void updateBmi() {
		this.bmi = calculateBmi();
	} // this method updates bmi based on the inputs by the user so it automatically
		// updates if units are changed

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getHeightCm() {
		return heightCm;
	}

	public void setHeightCm(double heightCm) {
		this.heightCm = heightCm;
	}

	public double getWeightKg() {
		return weightKg;
	}

	public void setWeightKg(double weightKg) {
		this.weightKg = weightKg;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
