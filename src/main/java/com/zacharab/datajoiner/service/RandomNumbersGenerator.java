package com.zacharab.datajoiner.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RandomNumbersGenerator {

    private final Random random = new Random();

    public List<Integer> getRandomIntegers(int minValue, int maxValue, int resultsNumber) {
        return random.ints(resultsNumber, minValue, maxValue)
                .boxed()
                .collect(Collectors.toList());
    }
}
