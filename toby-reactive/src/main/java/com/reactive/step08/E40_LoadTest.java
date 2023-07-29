package com.reactive.step08;

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
public class E40_LoadTest {
    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
//        basic("http://localhost:8080/step08/rest?idx={idx}");
        basic("http://localhost:8080/step08/rest/chain?idx={idx}");
    }

    private static void basic(String url) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate rt = new RestTemplate();

        StopWatch main = new StopWatch();
        main.start();

        CyclicBarrier barrier = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {
            es.submit(() -> {
                int idx = counter.addAndGet(1);

                barrier.await();

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
