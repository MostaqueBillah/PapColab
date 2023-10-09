package com.tafa.PapColab.services;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class apiservice {
    private final static String apiUrl = "https://api-inference.huggingface.co/models/nlptown/bert-base-multilingual-uncased-sentiment";
    private final static String apiToken = "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"; // Replace with your actual API token

    public static String performSentimentAnalysis(ArrayList<String> inputs) {
        // System.out.println(inputs);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.add("Authorization", "Bearer " + apiToken);
        String[] stringArray = inputs.toArray(String[]::new);
        // System.out.println("*********************************");
        // System.out.println(stringArray.length);
        // System.out.println(Arrays.toString(stringArray));
        // System.out.println("*********************************");
        MultiValueMap<String, String[]> body = new LinkedMultiValueMap<>();
        body.add("inputs", stringArray);
        // System.out.println("***************body******************");
        // System.out.println(body.toString());
        // System.out.println("***************body******************");
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(apiUrl, body, String.class, headers);
        // System.out.println("*************response********************");
        // System.out.println(response);
        // System.out.println("*************response********************");
        // System.out.println(response);
        return response;
    }
}

