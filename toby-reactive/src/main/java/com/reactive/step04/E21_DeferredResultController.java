package com.reactive.step04;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 1) localhost:8080/dr 수행 (응답이 오지 않은 대기 상태중)
 * --> dr.setResult("Hello " + msg); 로 값이 셋팅되기 전까지는 계속 대기하는것
 * 2) localhost:8080/dr/count 수행 -> 1
 * 3) localohost:8080/dr/event?msg=Result -> Hello Result
 * --> 대기 중이던 1)번에서 결과가 출력되면서 끝난다.
 *
 * 워커스레드가 따로 만들지 않는다.
 * DeferredResult Object 만 메모리에 유지가 되면, 위와 같은 방식으로 결과를 내려줄 수 있다.
 * Servlet 자원은 최소한으로 하고, 동시에 수많은 요청을 처리하는데 효율적이다.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class E21_DeferredResultController {
    // keep
    Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();


    @GetMapping("/dr")
    public DeferredResult<String> dr() {
        log.info("dr");
        DeferredResult<String> dr = new DeferredResult<>();

        results.add(dr);

        return dr;
    }

    @GetMapping("/dr/count")
    public String drCount() {
        return String.valueOf(results.size());
    }

    @GetMapping("/dr/event")
    public String drEvent(String msg) {
        for (DeferredResult<String> dr : results) {
            dr.setResult("Hello " + msg);
            results.remove(dr);
        }

        return "OK";
    }
}
