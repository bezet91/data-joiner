package com.zacharab.datajoiner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RandomOrgClient {

    private static final Logger log = LoggerFactory.getLogger(RandomOrgClient.class);

    private static final String URL = "https://random.org/integers/?format=plain&base=10&col=1";
    private static final String RANGE_MIN_VALUE_QUERY_PARAM = "&min=";
    private static final String RANGE_MAX_VALUE_QUERY_PARAM = "&max=";
    private static final String RESULTS_NUMBER_QUERY_PARAM = "&num=";

    private final RestTemplate restTemplate;

    @Autowired
    public RandomOrgClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Integer> getRandomIntegers(int minValue, int maxValue, int resultsNumber) {
        String fullURL = URL +
                RANGE_MIN_VALUE_QUERY_PARAM + minValue +
                RANGE_MAX_VALUE_QUERY_PARAM + maxValue +
                RESULTS_NUMBER_QUERY_PARAM + resultsNumber;
        try {
            String response = restTemplate.getForObject(fullURL, String.class);
            return convertResponse(response);
        } catch (RestClientResponseException e) {
            log.error("Error during connecting to random.org service", e);
            return Collections.emptyList();
        }
    }

    private List<Integer> convertResponse(String response) {
        try {
            return response.lines()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException | NullPointerException e) {
            log.error("Error during converting response", e);
            return Collections.emptyList();
        }
    }
}
