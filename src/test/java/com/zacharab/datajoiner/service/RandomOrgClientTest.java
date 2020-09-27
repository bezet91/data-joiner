package com.zacharab.datajoiner.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(RandomOrgClient.class)
public class RandomOrgClientTest {

    @Autowired
    private RandomOrgClient randomOrgClient;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void shouldMakeCorrectCall() {
        int minValue = 1;
        int maxValue = 100;
        int results = 3;
        String response = "530\n456\n309\n";

        this.server.expect(requestTo(
                "https://random.org/integers/?format=plain&base=10&col=1&min=" + minValue + "&max=" + maxValue + "&num=" + results))
                .andRespond(withSuccess(response, MediaType.TEXT_PLAIN));

        List<Integer> randomIntegers = randomOrgClient.getRandomIntegers(minValue, maxValue, results);

        assertThat(randomIntegers).isNotEmpty();
    }

    @Test
    public void shouldReturnEmptyListWhenServerIsDown() {
        int minValue = 1;
        int maxValue = 100;
        int results = 3;

        this.server.expect(requestTo(
                "https://random.org/integers/?format=plain&base=10&col=1&min=" + minValue + "&max=" + maxValue + "&num=" + results))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));

        List<Integer> randomIntegers = randomOrgClient.getRandomIntegers(minValue, maxValue, results);

        assertThat(randomIntegers).isEmpty();
    }
}