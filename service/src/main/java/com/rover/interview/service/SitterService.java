package com.rover.interview.service;

import com.rover.interview.model.Sitter;
import com.rover.interview.repository.SitterRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SitterService {

    private final SitterRepository sitterRepository;

    @Autowired
    public SitterService(SitterRepository sitterRepository) {
        this.sitterRepository = sitterRepository;
    }

    public void create(@NonNull Sitter sitter) {
        double sitterScore = calculateSitterScore(sitter);
        sitter.setSitterScore(sitterScore);
        sitter.setSitterRank(sitterScore);
        sitterRepository.save(sitter);
    }

    public void update(@NonNull Sitter sitter) {
        Assert.notNull(sitter.getId(), "Only saved Sitters can be updated");
        sitterRepository.save(sitter);
    }

    public Sitter findByEmail(@NonNull String email) {
        return sitterRepository.findByEmail(email);
    }

    public List<Sitter> listSitters() {
        return sitterRepository.findAll(Sort.by(Sort.Direction.DESC, "sitterRank"));
    }

    public List<Sitter> listSittersWithMinRatingsScore(double minRatingsScore) {
        return sitterRepository.findByMinRatingsScore(minRatingsScore);
    }

    private double calculateSitterScore(Sitter sitter) {
        // Sitter Score is 5 times the fraction of the English alphabet comprised
        // by the distinct letters in what we've recovered of the sitter's name
        Set<Character> distinctChars = new HashSet<>();
        for (Character c : sitter.getName().toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                distinctChars.add(c);
            }
        }
        int charsInAlphabet = 26;
        return (5.0 * distinctChars.size()) / charsInAlphabet;
    }
}
