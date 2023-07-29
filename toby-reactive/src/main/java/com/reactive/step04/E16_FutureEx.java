package com.reactive.step04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class E16_FutureEx {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newCachedThreadPool();

//        Thread.sleep(2000);
//        System.out.println("Hello");
//
//        System.out.println("Exit");

        /** 별도의 스레드로 실행해보자. */
        es.execute(() -> { // 매개변수, 리턴값이 없는 Runnable 구현
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            log.info("Async");
//            return "Hello"; // Runnable 은 리턴이 없다.
        });
        log.info("Exit");

        /** 리턴이 필요한 경우 : 별도의 스레드로 실행해보자. */
        es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception { // Callable 구현
                Thread.sleep(2000);
                log.info("Async");
                return "Hello";
            }
        });
        log.info("Exit");

        /** 리턴 결과를 main 스레드로 가져오고싶다.*/
        /** 리턴이 필요한 경우 : 별도의 스레드로 실행해보자. */
        Future<String> f = es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception { // Callable 구현
                Thread.sleep(2000);
                log.info("Async");
                return "Hello";
            }
        });

        System.out.println(f.isDone()); // false (수행 시점에 작업 완료 여부 출력)
        Thread.sleep(2000);
        log.info("Start"); // 제일 먼저 수행되겠다. get() 후인 "Exit"은 제일 마지막에 수행되겠다.
        System.out.println(f.isDone()); // true (true 일 경우 get()으로 값을 가져오는 로직을 사용할 수도 있다.)

        // 기다리면 Blocking, 기다리지 않으면 non-Blocking
        // 결과가 돌아올때까지 기다리므로 해당 라인은 Blocking(블로킹). 다음 라인은 완료된 후 수행한다.
        log.info(f.get()); // main 스레드에서 출력되는 값
        log.info("Exit"); // get() 이 호출된 이후 수행된다.
    }
}
