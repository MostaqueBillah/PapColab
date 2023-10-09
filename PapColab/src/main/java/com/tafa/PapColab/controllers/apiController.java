package com.tafa.PapColab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tafa.PapColab.services.apiservice;

@RestController
public class apiController {
    private final apiservice apiService;

    @Autowired
    public apiController(apiservice apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/analyzeSentiment")
    public String analyzeSentiment(@RequestBody String inputText) {
        return apiService.performSentimentAnalysis(inputText);
    }
}
