package com.fitness.tracker.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "workout_logs")
public class WorkoutLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title is required")
    private String title;

    private String description;

    private double caloriesBurned;

    @NotNull(message = "Workout date is required")
    private LocalDate workoutDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public WorkoutLog() {}

    public WorkoutLog(String title, String description, double caloriesBurned, LocalDate workoutDate, User user) {
        this.title = title;
        this.description = description;
        this.caloriesBurned = caloriesBurned;
        this.workoutDate = workoutDate;
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}