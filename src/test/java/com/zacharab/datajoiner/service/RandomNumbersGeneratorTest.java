package com.zacharab.datajoiner.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = RandomNumbersGenerator.class)
public class RandomNumbersGeneratorTest {

    @Autowired
    private RandomNumbersGenerator randomNumbersGenerator;

    @Test
    public void generatedIntegersShouldBeInGiveSize() {
        int minValue = 1;
        int maxValue = 100;
        int results = 3;

        List<Integer> randomIntegers = randomNumbersGenerator.getRandomIntegers(minValue, maxValue, results);

        assertThat(randomIntegers).hasSize(results);
    }

    @Test
    public void generatedIntegersShouldBeInGivenRange() {
        int minValue = 1;
        int maxValue = 100;
        int results = 3;

        List<Integer> randomIntegers = randomNumbersGenerator.getRandomIntegers(minValue, maxValue, results);

        randomIntegers.forEach(integer -> {
            assertThat(integer).isBetween(minValue, maxValue);
        });
    }
}
