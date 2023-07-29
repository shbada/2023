package com.reactive.step04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Slf4j
public class E17_FutureTaskEx {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        FutureTask<String> f = new FutureTask<>(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "Hello";
        }) {
            @Override
            protected void done() {
                // 비동기 작업이 모두 완료시 done()이 호출된다.
                try {
                    System.out.println(get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        es.execute(f); // f를 바로 던진다.
        es.shutdown(); // 이걸한다고 해서 비동기 수행이 끝나지않는다. 다만 해당 쓰레드가 계속 떠있는것
//
//        log.info("Start");
//        System.out.println(f.isDone());
//
//        log.info(f.get());
//        log.info("Exit");
    }
}
