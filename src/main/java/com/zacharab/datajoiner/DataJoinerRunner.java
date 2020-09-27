package com.zacharab.datajoiner;

import com.zacharab.datajoiner.service.NumbersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataJoinerRunner implements CommandLineRunner {

    private NumbersService numbersService;

    @Autowired
    public DataJoinerRunner(NumbersService numbersService) {
        this.numbersService = numbersService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(numbersService.getData());
    }
}