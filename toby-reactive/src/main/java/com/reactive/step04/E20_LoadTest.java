package com.reactive.step04;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 어떤 웹 요청이 동시에 100개 요청이 오면 100개의 스레드 생성하여 할당되는 과정 보기
 */
@Slf4j
public class E20_LoadTest {
    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        /**
         * 100개가 동시에 2초정도 걸림
         * 각 요청별로 스레드가 생성되어 총 스레드 100개가 생성되었다.
         */
//        basic("http://localhost:8080/async");

        /**
         * application.properties 에 'server.tomcat.max-threads=20' 추가 후 수행해보자.
         * 10초 정도 걸린다.
         * 스레드는 1 ~ 20까지 총 20개만 생성되었다.
         * 100개의 요청이 갔지만 스레드는 20개까지만 생성 가능하므로 나머지 80개는 큐에서 대기중이였다.
         * 스레드가 풀리면 다음 20개, 그 다음 20개.. 이렇게 되면서 2초짜리 작업 x 5  = 10초 정도 걸린다.
         */
//        basic("http://localhost:8080/async");

        /**
         * callable 호출해보자. (비동기)
         * 2초 걸렸다.
         * 스레드가 20개만 생성됨에도 위 동기 방식보다 훨씬 빠르다.
         *
         * 서블릿 스레드 / 작업 스레드
         * 서블릿 스레드는 20개로 돌려쓰지만, 2초짜리 작업인 작업 스레드는 100개를 만들어 사용한 것이다.
         */
//        basic("http://localhost:8080/callable");

        /**
         * application.properties 에 'server.tomcat.max-threads=1' 로 변경 후 수행해보자.
         * 2.3 초 안에 100개 요청이 처리되었다.
         *
         * 서블릿 스레드 / 작업 스레드
         * 서블릿 스레드는 1개로 돌려쓰지만, 2초짜리 작업인 작업 스레드는 100개를 만들어 사용한 것이다.
         *
         * -> 스레드 풀의 개수가 100개 정도인데, 긴 작업을 수행하는게 20개정도 있을때
         * 긴 작업은 작업 스레드가 수행하게하고, 그러는 사이에 서블릿 스레드는 빠르게 처리해서 마칠 수 있는 일반적인 스레드의 처리 작업에
         * 다 할당을 시키면 서블릿 스레드의 활용도가 높다.
         *
         * (결론)
         * 서블릿 스레드는 빠르게 반환되어 1개의 스레드를 가지고도 많은 요청을 처리할 수는 있다.
         */
//        basic("http://localhost:8080/callable");

        /**
         * E21_DeferredResultController.java 도 동일하게 수행해보자.
         * application.properties 에 'server.tomcat.max-threads=1' 에서 수행
         *
         * 100개 요청이 날라가면 모두 대기 상태다.
         * localhost:8080/dr/count 하면 100으로 출력된다.
         * localhost:8080/dr/event?msg=Async
         * 하면 응답이 한꺼번에 출력되고, dr/count 에는 0으로 출력된다.
         * 이벤트 1개로 100개의 요청에 한번에 응답을 주는 것이다.
         */
        basic("http://localhost:8080/dr");
    }

    private static void basic(String url) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();

        StopWatch main = new StopWatch();
        main.start();

        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                int idx = counter.addAndGet(1);
                log.info("Thread: {}", idx);

                StopWatch sw = new StopWatch();
                sw.start();

                rt.getForObject(url, String.class);

                sw.stop();
                log.info("Elapsed: " + idx + " -> " + sw.getTotalTimeMillis());
            });
        }

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS); // 대기작업이 끝날때까지 100초까지만 기다린다.
        main.stop();

        log.info("Total : {}", main.getTotalTimeSeconds());
    }
}
