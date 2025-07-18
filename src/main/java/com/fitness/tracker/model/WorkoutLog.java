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

    @NotNull(message = "Workout type is required")
    private String type;

    @NotNull(message = "Duration is required")
    private Integer durationMinutes;

    private double caloriesBurned;

    @NotNull(message = "Workout date is required")
    private LocalDate workoutDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public WorkoutLog() {}

    public WorkoutLog(String type, Integer durationMinutes, double caloriesBurned, LocalDate workoutDate, User user) {
        this.type = type;
        this.durationMinutes = durationMinutes;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
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