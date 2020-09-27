package com.zacharab.datajoiner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NumbersService {

    private static final Logger log = LoggerFactory.getLogger(NumbersService.class);

    private RandomNumbersGenerator randomNumbersGenerator;
    private RandomOrgClient randomOrgClient;

    @Value("${generator.range.min.value:1}")
    private int generatorRangeMinValue;
    @Value("${generator.range.max.value:1000}")
    private int generatorRangeMaxValue;
    @Value("${generator.results.number:10}")
    private int generatorResultsNumber;
    @Value("${randomOrg.range.min.value:1}")
    private int randomOrgRangeMinValue;
    @Value("${randomOrg.range.max.value:1000}")
    private int randomOrgRangeMaxValue;
    @Value("${randomOrg.results.number:10}")
    private int randomOrgResultsNumber;

    @Autowired
    public NumbersService(RandomNumbersGenerator randomNumbersGenerator, RandomOrgClient randomOrgClient) {
        this.randomNumbersGenerator = randomNumbersGenerator;
        this.randomOrgClient = randomOrgClient;
    }

    public List<Integer> getData() {
        List<Integer> randomIntegersFromGenerator = randomNumbersGenerator
                .getRandomIntegers(generatorRangeMinValue, generatorRangeMaxValue, generatorResultsNumber);
        log.info("Data fetched from java generator: {}", randomIntegersFromGenerator);
        List<Integer> randomIntegersFromRandomOrg = randomOrgClient
                .getRandomIntegers(randomOrgRangeMinValue, randomOrgRangeMaxValue, randomOrgResultsNumber);
        log.info("Data fetched from random.org: {}", randomIntegersFromRandomOrg);
        return Stream.concat(randomIntegersFromGenerator.stream(), randomIntegersFromRandomOrg.stream())
                .collect(Collectors.toList());
    }
}
