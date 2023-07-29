package com.reactive;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@SpringBootApplication
@Slf4j
@EnableAsync // @Async 비동기 수행을 위한 어노테이션 선언
public class ReactiveApplication {

    @Bean
    NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
        return new NettyReactiveWebServerFactory();
    }

    @Component
    public static class MyService {
        // 리스너 처리를 하고싶다면? ListenableFuture
        @Async // 비동기
        public ListenableFuture<String> hello() throws InterruptedException {
            log.info("hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("Hello"); // in Future
        }

//        public String hello() throws InterruptedException {
//            log.info("hello()");
//            Thread.sleep(1000);
//            return "Hello";
//        }
    }

    @RestController
    public static class Controller {
        @RequestMapping("/hello")
        public Publisher<String> hello(String name) {
            // Publisher 만 만들고, 이걸 던져주면 스프링이 원하는 방식으로 원하는 시점에 Subscriber 을 만들어서,
            // 우리가 던진 Publisher에게 데이터를 요청해서, (웹) 데이터를 받아 response body에서 사용된다.
            // 스프링이 해준다.
            return new Publisher<String>() {
                @Override
                public void subscribe(Subscriber<? super String> s) {
                    s.onSubscribe(new Subscription() {
                        @Override
                        public void request(long n) {
                            s.onNext("hello" + name);
                            s.onComplete();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                }
            };
        }
    }

    /**
     * bean으로 등록되어있으면 default 로 사용된다.
     * 아무것도 등록되지 않으면 Simple** 로 default 로 사용된다.
     * 여러개라면 @Async(value = "tp") value 에 빈 이름을 줄 수 있다.
     * @return
     */
    @Bean
    ThreadPoolTaskExecutor tp() { // ExecutorService 타입도 가능
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        // 스레드풀을 정한 개수만큼 만들어놓는다. 첫번째 요청이 오면 개수만큼 만든다.
        te.setCorePoolSize(10);
        // 풀이 꽉찼는데 요청이 오면 100개 까지 만든다 -> 이렇게 이해하면 틀리다.
        // corePoolSize 가 꽉차면 queueCapacity가 차고, queueCapacity가 꽉차면 maxPoolSize로 풀을 더 내준다.
        te.setMaxPoolSize(100);
        // 대기 큐를 몇개까지 가능할 것인가
        te.setQueueCapacity(50);
        te.setThreadNamePrefix("mythread"); // 스레드 이름 prefix 설정
        te.initialize();

        return te;
    }

    /**
     * @Async(value = "myThreadPool")
     * @return
     */
    @Bean
    ThreadPoolTaskExecutor myThreadPool() {
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        // 어떤 경우에도 스레드를 1개만 갖겠다는 설정
        te.setCorePoolSize(1); // core 가 다 차면, Queue를 채우고, Queue까지 다 찼을때 maxPoolSize까지 스레드 개수를 추가하다가 그때도 부족하면 에러가 난다.
        te.setMaxPoolSize(1);
        te.initialize();
        return te;
    }

    public static void main(String[] args) {
//        try (ConfigurableApplicationContext c = SpringApplication.run(ReactiveApplication.class, args)) {
//
//        }

        System.setProperty("reactor.ipc.netty.workerCount", "1");
        System.setProperty("reactor.ipc.netty.pool.maxConnections", "2000");
        SpringApplication.run(ReactiveApplication.class, args);
    }

    @Autowired
    MyService myService;

    @Bean
    ApplicationRunner run() {
        return args -> {
            log.info("run()");
//            String res = myService.hello();
//            log.info("exit: {}", res);

            // 비동기
//            Future<String> res = myService.hello();
            ListenableFuture<String> res = myService.hello();

            // callback
            res.addCallback(s -> System.out.println(s), e -> System.out.println(e.getMessage()));

            log.info("exit: {}", res.isDone());
            log.info("result: {}", res.get());
        };
    }

}
