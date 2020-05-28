package com.rover.interview.validation;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonPropertyOrder({"message", "details" })
public class ErrorResponse {

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private Map<String, String> details;

    public ErrorResponse(String message, Map<String, String> details) {
        this.message = message;
        this.details = details;
    }
}
