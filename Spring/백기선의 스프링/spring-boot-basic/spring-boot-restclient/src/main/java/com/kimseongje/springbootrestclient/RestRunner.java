package com.kimseongje.springbootrestclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestRunner implements ApplicationRunner {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;    // 동기 (Blocking)

    @Autowired
    WebClient.Builder builder;                  // 비동기 (Non-Blocking, callback)

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //RestTemplate restTemplate = restTemplateBuilder.build();
        WebClient webClient = builder.build();      // build() 하기 전에 커스터마이징 가능 (로컬) 또는 Customizer 를 빈으로 등록 (글로벌)
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        // TODO /hello
        //String helloResult = restTemplate.getForObject("http://localhost:8080/hello", String.class);
        Mono<String> helloResult = webClient.get()
                .uri("/hello")
                .retrieve()
                .bodyToMono(String.class);

        helloResult.subscribe(s -> {
            System.out.println(s);

            if (stopWatch.isRunning()) stopWatch.stop();

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });

        // TODO /world
        //String worldResult = restTemplate.getForObject("http://localhost:8080/world", String.class);
        Mono<String> worldResult = webClient.get()
                .uri("/world")
                .retrieve()
                .bodyToMono(String.class);

        worldResult.subscribe(s -> {
            System.out.println(s);

            if (stopWatch.isRunning()) stopWatch.stop();

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });

        stopWatch.stop();
    }
}
