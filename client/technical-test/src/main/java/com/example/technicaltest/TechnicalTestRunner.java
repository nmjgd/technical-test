package com.example.technicaltest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class TechnicalTestRunner implements ApplicationRunner {
    @Autowired
    private TechnicalTest technicalTest;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        technicalTest.execute(args);
    }
}
