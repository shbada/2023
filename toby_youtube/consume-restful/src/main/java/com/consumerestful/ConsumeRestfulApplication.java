package com.consumerestful;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

record Quote(String type, Value value) {
}

record Value(long id, String quote) {

}

@SpringBootApplication
public class ConsumeRestfulApplication {

    @Bean
    ApplicationRunner run(RestTemplate restTemplate) {
        return args -> {
            Quote quote = restTemplate.getForObject("http://localhost:8080/api/random", Quote.class);
            System.out.println(quote);
        };
    }

    /**
     * application 전체에서 공유
     */
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) { // springboot web module 자동구성에서 만들어줌
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumeRestfulApplication.class, args);
    }

}
