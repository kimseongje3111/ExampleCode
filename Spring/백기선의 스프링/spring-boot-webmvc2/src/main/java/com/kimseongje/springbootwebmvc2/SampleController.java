package com.kimseongje.springbootwebmvc2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name","Seongje"); // 모델에 속성 부여
        return "hello";
    }
}
