package com.reactive.step02;

import reactor.core.publisher.Flux;

/**
 * Reactor
 * implementation 'io.projectreactor:reactor-core:3.4.23'
 */
public class ReactorEx {
    public static void main(String[] args) {
        Flux.<Integer>create(s -> {
            s.next(1);
            s.next(2);
            s.next(3);
            s.complete();
        })
        .log() // 오가는 데이터의 로그를 출력
        .map(s -> s * 10)
        .reduce(0, (a, b) -> a + b)
        .log()
        .subscribe(System.out::println); // onNext()
    }
}
