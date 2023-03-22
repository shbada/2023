package com.example.springbootac;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootAutoconfigurationApplication {
    @Bean
    ApplicationRunner run(ConditionEvaluationReport report) {
        return args -> {
            report.getConditionAndOutcomesBySource().entrySet().stream()
                    .filter(co -> co.getValue().isFullMatch()) // 여러 조건들을 모두 매칭해서 빈 등록할것인가
                    .forEach(co -> {
                        System.out.println(co.getKey());
                    });
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAutoconfigurationApplication.class, args);
    }

}
