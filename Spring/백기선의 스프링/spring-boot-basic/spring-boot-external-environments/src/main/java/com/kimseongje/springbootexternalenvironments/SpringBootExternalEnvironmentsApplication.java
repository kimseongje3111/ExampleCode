package com.kimseongje.springbootexternalenvironments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class SpringBootExternalEnvironmentsApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBootExternalEnvironmentsApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
