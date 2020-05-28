package com.rover.interview.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rover.interview.web.service.SitterSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;

@Controller
public class SearchResultsController {

    private static final Logger LOG = LoggerFactory.getLogger(SearchResultsController.class);

    private SitterSearchService sitterSearchService;

    @Autowired
    public SearchResultsController(SitterSearchService sitterSearchService) {
        this.sitterSearchService = sitterSearchService;
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/results")
    public String getResults(@RequestParam(required = false) Integer minRatingsScore, Model model) {
        try {
            model.addAttribute("sitters", sitterSearchService.getSitters(minRatingsScore));
        } catch (JsonProcessingException | ResourceAccessException e) {
            LOG.error("Failed to retrieve sitter results with minRatingsScore=" + minRatingsScore, e);
        }
        return "sitters";
    }
}
