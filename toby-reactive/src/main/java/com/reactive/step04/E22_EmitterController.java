package com.reactive.step04;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class E22_EmitterController {
    // keep
    Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();

    /**
     * ResponseBodyEmitter
     * HTTP 안에 Data를 여러번에 나눠서 보낼 수 있다.
     * 하나의 요청에 여러번 응답을 보낼 수 있다.
     * @return\
     */
    @GetMapping("/emitter")
    public ResponseBodyEmitter dr() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        Executors.newSingleThreadExecutor().submit(() -> {
            for (int i = 1; i <= 50; i++) {
                try {
                    // 50 번 나눠서 응답을 내려준다.
                    emitter.send("<p>Stream " + i + "</p>");
                    Thread.sleep(200);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return emitter;
    }
}
