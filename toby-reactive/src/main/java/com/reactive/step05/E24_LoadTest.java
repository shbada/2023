package com.reactive.step05;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 어떤 웹 요청이 동시에 100개 요청이 오면 100개의 스레드 생성하여 할당되는 과정 보기
 * - Java VisualVM
 * - JMC (Java Mission Control)
 */
@Slf4j
public class E24_LoadTest {
    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        /**
         * 요청개수 : 100개
         * 걸린시간 : 0.36
         * server.tomcat.max-threads=1 일 경우
         * - http-nio-8080-exec-1 스레드 1개만 생성됨 -> 1개가 순차적으로 100개를 처리
         * - 단순 출력 로직이므로 비동기가 아니여도 빠르겠다.
         */
        log.info("main");
//        basic("http://localhost:8080/rest?idx={idx}");

        /**
         * /rest -> /service(8081) 외부 서비스 호출하듯 다른 서비스 호출하는 로직 추가
         * /service 로직 안에 Thread.sleep(2000); 추가하여 2초가 걸리게 해보자.
         * 첫번째 요청 : 2초
         * 두번째 요청 : 4초
         * 세번째 요청 : 6초 ... 이렇게 2초씩 경과된다.
         * /rest 의 rt.getForObject(/service).. 코드가 블로킹이라 결과가 오기 전까지 대기해야하기 때문. (2초가 걸린다고 본다.)
         *
         * 어떻게 해결할것인가?
         * API 호출 로직을 비동기로 바꿔야한다.
         * 바꾼 후로, 총 시간은 2초가 걸린다.
         * 스레드는 1개인데, 순차적으로 돌렸고 스레드를 재활용해가면서 수행되는데,
         * 비동기적으로 수행되므로 즉시 리턴된다. (백그라운드에서 비동기적으로 외부 서버를 호출하고 결과가 오면 스프링 MVC 에게 리턴하고, 결과값을 리턴한다.)
         * 스레드는 1개뿐이지만 100개의 요청을 처리하는데 2초가 걸렸다.
         *
         * 스레드 생성 상태를 보면, 스레드가 100가 있다..
         * 기본적으로 AsyncRestTemplate 를 사용하여 호출하면 비동기적으로 즉시 리턴하는건 맞는데,
         * 백그라운드 스레드를 만들어놓고 이 스레드를 사용해서 기본적인 자바의 네트워크 API를 사용해서 호출하는 코드가 수행된다.
         * 하나의 스레드로 한꺼번에 처리가 된것처럼 보이지만, 서버의 자원을 100개 더 사용한 상황이다.
         * 이는 우리가 원하는 바람직한 상황은 아니다.
         *
         * -> Netty 설정 추가하여 해결하자.
         *
         * 최종 버전
         * - 총 3개의 스레드
         * - http-nio-8080-exec-1 (Tomcat)
         * - nioEventLoopGroup-2-1 (Netty)
         * - myThreadPool-1 (MyService)
         */
        basic("http://localhost:8080/rest?idx={idx}");
    }

    private static void basic(String url) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();

        StopWatch main = new StopWatch();
        main.start();

        /*
            스레드풀을 100개를 만들어놓고, 100번 루프를 돌면서 스레드를 하나하나 만드는 것
            빠른 속도로 쓰레드가 생성되서 동시에 되는것처럼 보이지만, 이는 순차적으로 100개의 스레드가 만들어지면서
            요청을 만드는 코드가 순차적으로 수행되는 것이다.
            100개의 스레드를 전부 생성하고 동시에 만약 100개를 딱 실행시키고 싶다면?
            -> 이는 스레드의 동기화 기법 (CyclicBarrier)
         */
        CyclicBarrier barrier = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {
            es.submit(() -> {
                int idx = counter.addAndGet(1);

                // 이 코드를 만난 순간, 100번째 스레들르 생성해서 여기에 오면 블로킹되었던 100개가 한꺼번에 아래 코드에 진입된다.
                barrier.await();
                // 에러처리가 람다식 안에 있으면 문제다. Runnable 은 기본적으로 Exception을 던지도록 정의되어있지 않다.
                // 밖으로 던질 방법은 없고 대신 try~catch로 묶어야한다.
                // 이 방법 대신 Callable을 구현하도록 하자. (es.submit()으로 변경) -> Excpetion을 던지도록 되어있다.

                log.info("Thread: {}", idx);

                StopWatch sw = new StopWatch();
                sw.start();

                String res = rt.getForObject(url, String.class, idx);

                sw.stop();
                log.info("Elapsed: {} {} / {}", idx, sw.getTotalTimeMillis(), res);

                return null; // 이 한줄이 추가되면, Callable 로 컴파일러가 유추하게된다.
            });
        }

        // 101개 - 여기에 도달하는 순간에 전체를 보낸다.!
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS); // 대기작업이 끝날때까지 100초까지만 기다린다.
        main.stop();

        log.info("Total : {}", main.getTotalTimeSeconds());
    }
}
