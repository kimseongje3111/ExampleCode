package com.kimseongje.springbootapplication;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);

        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.setBanner(new Banner() {
            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                out.println("=============================");
                out.println("KIMSEONGJE SPRING");
                out.println("=============================");
            }
        });

        springApplication.addListeners(new SampleListener());
        springApplication.run(args);
    }

}
