package com.kimseongje.springbootrestclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleCotroller {

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(5000l);
        return "hello";
    }

    @GetMapping("/world")
    public String world() throws InterruptedException {
        Thread.sleep(3000l);
        return "world";
    }
}
