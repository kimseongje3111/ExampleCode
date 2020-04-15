package com.kimseongje.springbootlogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(Runner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("====================================");
        logger.debug("HELLO WORLD!");
        logger.debug("====================================");
    }
}
