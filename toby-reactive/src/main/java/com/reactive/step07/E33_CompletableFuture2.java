package com.reactive.step07;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
public class E33_CompletableFuture2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Runnable
        /*
            [main] INFO com.reactive.step07.E32_CompletableFuture2 - exit
            [ForkJoinPool.commonPool-worker-19] INFO com.reactive.step07.E32_CompletableFuture2 - runAsync

            CompletionStage : 하나의 비동기 작업을 수행하고, 이게 완료가 됬을때 의존적으로 또다른 작업을 수행할 수 있도록 하는 것
         */
        CompletableFuture.runAsync(() -> log.info("runAsync"));
        CompletableFuture
                /** 계속 같은 스레드 */
//                .runAsync(() -> log.info("runAsync")) // 수행된 백그라운드 스레드 (결과값을 사용할 수 없다)
                .supplyAsync(() -> { // 파라미터는 받지 않고 리턴값은 존재
                    log.info("supplyAsync");
//                    고의 에러 발생
//                    if (1 == 1) {
//                        throw new RuntimeException();
//                    }

                    return 1;
                })
                .thenApply(s -> { log.info("thenApply1 {}", s);  // 파라미터, 결과값이 존재
                    return s + 1;
                })
                .thenCompose(s -> { log.info("thenCompose {}", s);  // 파라미터, 결과값이 존재
                    return CompletableFuture.completedFuture(s + 1);
                    // CompletableFuture 객체를 리턴하게된다. (그 안에 CompletableFuture)
                    // thenCompose 로 써야한다.(flatMap과 유사)
                    // CompletableFuture.completedFuture(s + 1) 로 리턴해야하는 상황이 있을때 사용한다.
                })
                .thenApply(s2 -> {
                    log.info("thenApply2 {}", s2);
                    return s2 * 3;
                }) // 위 결과를 받아서 소모만 한다.
                .exceptionally(e -> -10) // 위 어디서든 예외가 발생하면 예외 처리하고싶다.
                .thenAccept(s3 -> log.info("thenAccept {}", s3))
        ;

        log.info("exit");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
