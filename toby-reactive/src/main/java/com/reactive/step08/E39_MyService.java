package com.reactive.step08;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 기본적인 비동기 수행되도록 설정
 * - 스레드를 많이 생성하므로 개수 설정
 */
@Service
@EnableAsync
public class E39_MyService {
    /**
     * 별개의 스레드로 수행시키자.
     * @param req
     * @return
     */
    @Async
    public CompletableFuture<String> work(String req) {
        return CompletableFuture.completedFuture(req + "/asyncwork");
    }
}
