package com.reactive.step09;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Spring5 사용
 * Netty
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class E41_MonoController {
    @GetMapping("/mono1")
    Mono<String> hello1() {
        // Mono : Publusher 을 만들고, (Publisher) 연결하고, 마지막에 Subscriber에 전달
        // Publisher -> (Publisher) -> (Publisher) -> Subscriber
        //           <- 구독         <- 구독         <- 구독
        /*
            onSubscribe()
            request(1) : Mono는 데이터를 보통 1개만 가지고있다
            onNext(Hello Webflux)
            request(1) : 추가가 있는지?
            request(31) : ?
            onComplete() : 정상완료시
            cancel()
         */
        Mono m = Mono.just("Hello Webflux").log(); // subscribe() 됬을때의 로그
        return m;
    }

    @GetMapping("/mono2")
    Mono<String> hello2() {
/*
            pos1
            pos2
            onSubscribe()
            request(1)
            onNext(Hello Webflux)
            ...
        */
        log.info("pos1");
        Mono m = Mono.just("Hello Webflux").log(); // subscribe() 됬을때의 로그
        log.info("pos2");

        return m;
    }

    @GetMapping("/mono3")
    Mono<String> hello3() {
/*
            // 이부분은 사실상 동기적인 코드다.
            // doOnNext 수행 후, log() 수행
            // 값을 받아서 출력 또는 로그 남기는 -> 이는 동기적이라도 스프링에 넘어가고, 스프링에서 subscribe()하는 그 시점에 호출이 된다. (미리 만들어놓은 Mono의 호출 시점)
            pos1
            pos2
            onSubscribe()
            request(1)
            Hello Webflux // 직접 넘어가는 데이터를 찍어보겠다
            onNext(Hello Webflux)
            request(1)
            request(31)
            ...
        */
        log.info("pos1");
        // 비동기 방식
        Mono m2 = Mono.just("Hello Webflux").doOnNext(c -> log.info(c)).log(); // subscribe() 됬을때의 로그
        return m2;
    }

//    @GetMapping("/mono4")
//    Mono<String> hello4() {
//        // myService.findById(1) 가 먼저 수행되고, 이 결과값이 just에 들어간다.
//        Mono m3 = Mono.just(myService.findById(1)).doOnNext(c -> log.info(c)).log();
//        return m3;
//    }

    @GetMapping("/mono5")
    Mono<String> hello5() {
/*
            pos1
            method generateHello()
            pos2
            이후 Mono 안의 로직 수행
            onSubscribe()
            request(1)
            Hello Mono
            onNext(Hello Mono)
            ...
         */
        log.info("pos1");
        Mono m4 = Mono.just(generateHello()).doOnNext(c -> log.info(c)).log();
        log.info("pos2");
        return m4;
    }

    @GetMapping("/mono6")
    Mono<String> hello6() {
        /*
            pos1
            pos2
            onSubscribe()
            request(1)
            method generateHello() // 수행된 이후
            Hello Mono //doOnNext()가 수행됨
            onNext(Hello Mono)
            request(1)
            request(31)
            onComplete()
            onCancel()
            ...
         */
        Mono<String> m5 = Mono.fromSupplier(() -> generateHello()).doOnNext(c -> log.info(c)).log();

        // mono -> String
        /*
            pos1
            method generateHello() // 수행된 이후
             onSubscribe()
             request(unbounded)
            Hello Mono //doOnNext()가 수행됨
             onNext(Hello Mono)
             onComplete()
            pos2 : Hello Mono // block()가 subscribe()를 돌린다. (이 시점에서 Mono 결과를 가져올때까지 block 된다.)
             onSubscribe()
             request(1)
            Hello Mono
             onNext(Hello Mono)
             request(1)
             request(31)
             onComplete()
             onCancel()
            ...
         */
        String blockMsg = m5.block();
        // mono -> String
        log.info("pos2: {}", blockMsg);

        return m5;
    }

    @GetMapping("/mono")
    Mono<String> hello() {

        /*
            subscribe() 하는건 여러개가 있을 수 있다.
            // Hot : 새로운 구독을 하더라도 구독 이후의 시점의 실시간 데이터를 통지한다.
            // Cold : 데이터가 만들어져서 고정되어 어느 Subscriber 가 요청하던 데이터가 미리 셋팅되어 전송
            // -> 새로운 구독이 발생해도 동일한 데이터를 통지한다.

            pos1
             onSubscribe()
             request(1)
            method generateHello() // 수행된 이후
            Hello Mono //doOnNext()가 수행됨
            pos2
            // subscribe() 후
            onSubscribe()
            request(1)
            method generateHello() // 수행된 이후
            Hello Mono //doOnNext()가 수행됨
            onNext(Hello Mono)
            request(1)
            request(31)
            onComplete()
            onCancel()
            ...
         */
        Mono<String> m5 = Mono.fromSupplier(() -> generateHello()).doOnNext(c -> log.info(c)).log();

        m5.subscribe(); // subscribe() 하고 리턴하면?
        return m5;
    }

    @GetMapping("/mono/block")
    Mono<String> helloMono() {

        /*
            pos1
            method generateHello() // 수행된 이후
             onSubscribe()
             request(unbounded)
            Hello Mono //doOnNext()가 수행됨
             onNext(Hello Mono)
             onComplete()
            pos2 : Hello Mono // block()가 subscribe()를 돌린다. (이 시점에서 Mono 결과를 가져올때까지 block 된다.)
             onSubscribe()
             request(1)
            Hello Mono
             onNext(Hello Mono)
             request(1)
             request(31)
             onComplete()
             onCancel()
            ...
         */
        Mono<String> m6 = Mono.fromSupplier(() -> generateHello()).doOnNext(c -> log.info(c)).log();
        String blockMsg6 = m6.block();
        log.info("pos2: {}", blockMsg6);
        return m6;
    }

    private String generateHello() {
        log.info("method generateHello()");
        return "Hello Mono";
    }
}
