package com.fitness.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.tracker.model.BodyStat;
import com.fitness.tracker.model.User;
import com.fitness.tracker.repository.BodyStatRepository;

@Service
public class BodyStatService {

    private final BodyStatRepository bodyStatRepository; //final means it must be assigned once

    public BodyStatService(BodyStatRepository bodyStatRepository) { //constructor injection
        this.bodyStatRepository = bodyStatRepository;
    }

    // Save or update a BodyStat record
    public BodyStat saveBodyStat(BodyStat bodyStat) {
        return bodyStatRepository.save(bodyStat);
    }

    // Get all BodyStat records for a specific user
    public List<BodyStat> getBodyStatsByUser(User user) {
        return bodyStatRepository.findByUser(user);
    }
    
    public List<BodyStat> getAllBodyStats(User user) {
        return bodyStatRepository.findByUserOrderByDateAsc(user);
    }

    // get latest BodyStat for a user
    public BodyStat getLatestBodyStat(User user) {
        List<BodyStat> stats = bodyStatRepository.findByUserOrderByDateDesc(user);
        if (stats.isEmpty()) {
            return null;
        }
        return stats.get(0);
    }
}