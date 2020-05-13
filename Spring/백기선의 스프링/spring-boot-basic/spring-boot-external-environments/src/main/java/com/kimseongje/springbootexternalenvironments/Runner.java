package com.kimseongje.springbootexternalenvironments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

    @Autowired
    private SeongjeProperties seongjeProperties;

    @Autowired
    private String hello;

    @Value("${kim.fullName}")
    private String name;

    @Value("${kim.age}")
    private int age;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("==========================");
        System.out.println(seongjeProperties.getName());
        System.out.println(seongjeProperties.getAge());
        System.out.println(seongjeProperties.getFullName());
        System.out.println(seongjeProperties.getSessionTime());
        System.out.println("==========================");

        System.out.println("==========================");
        System.out.println(hello);
        System.out.println("==========================");
    }
}
