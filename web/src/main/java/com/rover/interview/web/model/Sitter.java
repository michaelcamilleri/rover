package com.rover.interview.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sitter {

    private String name;
    private String imageUrl;
    private Integer ratingsScore;

    public void setRatingsScore(Double score) {
        ratingsScore = (int) Math.round(score);
    }
}
