package com.reactive.step03;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class E11_SubscribeSchedulerEx {
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                /**
                 * 별도의 스레드로 동작시키고싶다.
                 * Scheduler 방식 -> publisherOn, subscribeOn 둘 중 하나를 선택하면 된다.
                 */
                @Override
                public void request(long n) {
                    log.debug("request()");
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        }; // pub

        // pub-sub 연결
        Publisher<Integer> subOnPub = new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                // subscribe의 과정을 별도 스레드로 수행하고 싶다.
                // newSingleThreadExecutor() : 한번에 1개의 스레드만 동작함을 보장한다.
                // Publihser 가 느린 경우, 이걸 처리하는 쪽은 빠른 경우
                // 이렇게하면 main 스레드는 블로킹 없이 바로 수행된다.
                ExecutorService es = Executors.newSingleThreadExecutor(); // 코어 스레드 1, Maximum 스레드 1

//                pub.subscribe(sub); // 구독
                es.execute(() -> pub.subscribe(sub));
            }
        };

        // sub
        subOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError: {}", t);
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        });

        // main 쓰레드는 위 로직이 모두 수행되고 나서야, exit 을 출력하고 종료한다.
        log.info("exit");
    }
}
