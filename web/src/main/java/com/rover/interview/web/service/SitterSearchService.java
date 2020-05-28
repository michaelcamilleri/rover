package com.rover.interview.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rover.interview.web.model.Sitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SitterSearchService {

    private final String url;

    public SitterSearchService(@Value("${sitters.service.url}") String url) {
        this.url = url;
    }

    public List<Sitter> getSitters(Integer minRatingsScore) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = minRatingsScore == null ? url : url + "?minRatingsScore=" + (minRatingsScore - 0.5);
        String response = restTemplate.getForObject(requestUrl, String.class);
        Sitter[] sitters = StringUtils.isEmpty(response) ? new Sitter[]{} : new ObjectMapper().readValue(response, Sitter[].class);
        return Arrays.asList(sitters);
    }
}
