package com.reactive.step03;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class E13_FluxScEx {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .publishOn(Schedulers.newSingle("pub")) // pub-1 thread
                .log()
                .subscribeOn(Schedulers.newSingle("sub")) // sub-1 thread
                .subscribe(System.out::println);

        System.out.println("exit"); // main thread
    }
}
