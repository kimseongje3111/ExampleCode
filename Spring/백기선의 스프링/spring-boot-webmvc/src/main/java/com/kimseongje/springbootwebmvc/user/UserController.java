package com.kimseongje.springbootwebmvc.user;

import org.springframework.web.bind.annotation.*;

@RestController // @ResponseBody 생략 가능
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/users/create")
    public User create(@RequestBody User user) {    // @RequestBody로 요청된 본문의 body를 User 객체로 컨버팅 또는 그 반대로 수행
        return user;
    }

//    @PostMapping("/user")
//    public @ResponseBody User create(@RequestBody User user) {
//        return null;
//    }


}
