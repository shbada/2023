package com.reactive.step07;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 기본적인 비동기 수행되도록 설정
 * - 스레드를 많이 생성하므로 개수 설정
 */
@Service
@EnableAsync
public class E36_MyService {
    public String work(String req) {
        return req + "/asyncwork";
    }
}
