package com.reactive.step07;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class E34_CompletableFuture_otherThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 10개의 스레드를 다 쓰고, 앞으로 돌아가서 재사용하는 방식
        ExecutorService es = Executors.newFixedThreadPool(10);

        /*
            [pool-1-thread-1] INFO com.reactive.step07.E33_CompletableFuture_otherThread - supplyAsync
            [pool-1-thread-2] INFO com.reactive.step07.E33_CompletableFuture_otherThread - thenApply1 1
            [pool-1-thread-2] INFO com.reactive.step07.E33_CompletableFuture_otherThread - thenCompose 2
            [pool-1-thread-2] INFO com.reactive.step07.E33_CompletableFuture_otherThread - thenApply2 3
            [pool-1-thread-3] INFO com.reactive.step07.E33_CompletableFuture_otherThread - thenAccept 9
         */
        CompletableFuture
                .supplyAsync(() -> { // 파라미터는 받지 않고 리턴값은 존재
                    log.info("supplyAsync");
//                    고의 에러 발생
//                    if (1 == 1) {
//                        throw new RuntimeException();
//                    }

                    return 1;
                }, es)  /** es 명시 */
                /** 다른 스레드로 수행하고 싶다. thenApply -> thenApplyAsync */
                .thenApplyAsync(s -> { log.info("thenApply1 {}", s);  // 파라미터, 결과값이 존재
                    return s + 1;
                }, es) /** es 명시 */
                .thenCompose(s -> { log.info("thenCompose {}", s);  // 파라미터, 결과값이 존재
                    return CompletableFuture.completedFuture(s + 1);
                })
                .thenApply(s2 -> {
                    log.info("thenApply2 {}", s2);
                    return s2 * 3;
                })
                .exceptionally(e -> -10)
                .thenAcceptAsync(s3 -> log.info("thenAccept {}", s3), es)  /** es 명시 */
        ;

        log.info("exit");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
