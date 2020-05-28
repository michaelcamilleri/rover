package com.rover.interview.controller;

import com.rover.interview.model.Sitter;
import com.rover.interview.service.SitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
public class SitterController {

    private final SitterService sitterService;

    @Autowired
    public SitterController(SitterService sitterService) {
        this.sitterService = sitterService;
    }

    @GetMapping("/sitter/{email}")
    public Sitter getSitterByEmail(@PathVariable @NotEmpty String email) {
        return sitterService.findByEmail(email);
    }

    @GetMapping("/sitters")
    public List<Sitter> listSitters(@RequestParam(required = false) Double minRatingsScore) {
        return minRatingsScore == null ? sitterService.listSitters() : sitterService.listSittersWithMinRatingsScore(minRatingsScore);
    }

    // TODO Add Create, Update and Delete methods
}
