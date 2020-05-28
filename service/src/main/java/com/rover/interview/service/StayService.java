package com.rover.interview.service;

import com.rover.interview.model.Sitter;
import com.rover.interview.model.Stay;
import com.rover.interview.repository.StayRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class StayService {

    private final StayRepository stayRepository;

    private final SitterService sitterService;

    @Autowired
    public StayService(StayRepository stayRepository,
                       SitterService sitterService) {
        this.stayRepository = stayRepository;
        this.sitterService = sitterService;
    }

    public void create(@NonNull Stay stay) {
        Assert.isTrue(stay.getEndDate().compareTo(stay.getStartDate()) > 0, "End date must occur after start date");
        stayRepository.save(stay);
        updateSitterScores(stay.getSitter(), stay.getRating());
    }

    public Stay getStay(@NonNull Long id) {
        return stayRepository.findById(id).orElse(null);
    }

    public void updateSitterScores(Sitter sitter, Integer latestRating) {
        int currentStayCount = sitter.getStayCount();
        int newStayCount = currentStayCount + 1;
        double currentAverageScore = currentStayCount == 0 ? latestRating : sitter.getRatingsScore();
        double newAverageScore = ((currentAverageScore * newStayCount) + latestRating) / (newStayCount + 1);
        sitter.setRatingsScore(newAverageScore);

        if (currentStayCount < 10) {
            // The Overall Sitter Rank is a weighted average of the Sitter Score and Ratings Score, weighted by the number of stays
            double weightedSitterScore = sitter.getSitterScore() * ((10 - newStayCount) / 10.0);
            double weightedAverageScore = newAverageScore * (newStayCount / 10.0);
            double weightedScore = weightedSitterScore + weightedAverageScore;
            sitter.setSitterRank(weightedScore);
        } else {
            // When a sitter has 10 or more stays, their Overall Sitter Rank is equal to the Ratings Score
            sitter.setSitterRank(newAverageScore);
        }
        sitter.setStayCount(newStayCount);
        sitterService.update(sitter);
    }
}
