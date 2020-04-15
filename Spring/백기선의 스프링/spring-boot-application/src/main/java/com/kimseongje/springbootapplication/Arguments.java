package com.kimseongje.springbootapplication;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class Arguments {

    public Arguments(ApplicationArguments arguments) {
        System.out.println("foo: " + arguments.containsOption("foo"));
        System.out.println("bar: " + arguments.containsOption("bar"));
    }
}
