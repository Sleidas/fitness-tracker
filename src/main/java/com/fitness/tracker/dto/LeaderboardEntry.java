package com.fitness.tracker.dto;

public class LeaderboardEntry {

    private String friendName;
    private double totalCalories;
    private int totalWorkouts;
    private long activeDays;

    public LeaderboardEntry() {}
    
    public LeaderboardEntry(String friendName, double totalCalories, int totalWorkouts, long activeDays) {
        this.friendName = friendName;
        this.totalCalories = totalCalories;
        this.totalWorkouts = totalWorkouts;
        this.activeDays = activeDays;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getTotalWorkouts() {
        return totalWorkouts;
    }

    public void setTotalWorkouts(int totalWorkouts) {
        this.totalWorkouts = totalWorkouts;
    }

    public long getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(long activeDays) {
        this.activeDays = activeDays;
    }
}