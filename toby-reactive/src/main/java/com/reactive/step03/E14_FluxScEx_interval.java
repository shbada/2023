package com.reactive.step03;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class E14_FluxScEx_interval {
    public static void main(String[] args) throws InterruptedException {
        // 주기를 가지고 계속 수행
        // main 스레드에서 수행해보면 출력이 안된다. -> sleep() 추가 필요
        // interval : 데몬스레드를 만들기 때문이다.
        // JVM이 유저 스레드가 하나도 없고 데몬 스레드만 있으면 강제로 종료시킨다.
        Flux.interval(Duration.ofMillis(500))
                .take(10) // 10개만 받고 끝낸다.
                .subscribe(s -> log.debug("onNext:{}", s)); // 별도의 스레드에서 수행된다.

        log.debug("exit1");
        TimeUnit.SECONDS.sleep(5);

        // main 스레드가 종료되도 이 스레드는 작업을 마치기 전까지는 JVM이 내려가지 않는다.
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {}

            System.out.println("Hello");
        });

        System.out.println("exit2");
    }
}
