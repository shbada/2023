package com.reactive.step04;

import com.reactive.ReactiveApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@Slf4j
public class E19_MyController {
    @GetMapping("/async")
    public String async() throws InterruptedException {
        // 이 로직을 수행하는 동안 블로킹 되는걸 원치않는다. -> callable 로 변경
        Thread.sleep(2000);
        return "hello";
    }

    /**
     * http-nio-8080-exec-3 (tomcat 위에서 도는 쓰레드)
     * main에서 실행하는게 아니므로 main 쓰레드가 아니다.
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/callable")
    public Callable<String> callable() throws InterruptedException {
        log.info("callable"); // http-nio-8080-exec-3

        return () -> {
            log.info("async"); // console 에는 이미 찍히고, (MvcAsync1 이라는 별도의 쓰레드로 실행 - 스프링)
            Thread.sleep(2000);
            return "hello"; // 결과를 클라이언트에 내려준다.
        };

//        return new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                log.info("async"); // console 에는 이미 찍히고, (MvcAsync1 이라는 별도의 쓰레드로 실행 - 스프링)
//                Thread.sleep(2000);
//                return "hello"; // 결과를 클라이언트에 내려준다.
//            }
//        };
    }
}
