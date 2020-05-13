package com.kimseongje.springbootexternalenvironments;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;

@Component
@ConfigurationProperties("kim") // 외부 프로퍼티 적용
@Validated  // 검증
public class SeongjeProperties {

    @NotEmpty
    private String name;
    private int age;
    private String fullName;
    private Duration sessionTime = Duration.ofSeconds(30);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Duration getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Duration sessionTime) {
        this.sessionTime = sessionTime;
    }
}
