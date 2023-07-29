package com.reactive.step03;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class E12_PublisherSchedulerEx {
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

        /*
            [pool-2-thread-1] DEBUG com.reactive.step03.E12_PublisherSchedulerEx - onSubscribe
            [pool-2-thread-1] DEBUG com.reactive.step03.E12_PublisherSchedulerEx - request()
         */
        Publisher<Integer> subOnPub = sub -> {
            ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory() {
                // thread factory 커스텀 추가
                @Override
                public String getThreadNamePrefix() {
                    return "subOn-";
                }
            }); // 코어 스레드 1, Maximum 스레드 1

            es.execute(() -> pub.subscribe(sub));
        };

        /*
            [pool-1-thread-1] DEBUG com.reactive.step03.E12_PublisherSchedulerEx - onNext: 1
            [pool-1-thread-1] DEBUG com.reactive.step03.E12_PublisherSchedulerEx - onNext: 2
            [pool-1-thread-1] DEBUG com.reactive.step03.E12_PublisherSchedulerEx - onNext: 3
            [pool-1-thread-1] DEBUG com.reactive.step03.E12_PublisherSchedulerEx - onNext: 4
            [pool-1-thread-1] DEBUG com.reactive.step03.E12_PublisherSchedulerEx - onNext: 5
            [pool-1-thread-1] DEBUG com.reactive.step03.E12_PublisherSchedulerEx - onComplete
         */
        // pub-sub 연결
        Publisher<Integer> pubOnPub = sub -> {
            /**
             * 각 메서드를 별도 스레드로 수행
             * Publisher 처리가 늦어졌을때 사용
             */
            subOnPub.subscribe(new Subscriber<Integer>() {
                // 요청이 동시에 날라와도, 순서대로 수행
                // 하나의 Publihser가 데이터를 던져주는 것은 멀티 스레드로는 불가능, 단일 스레드로만 처리된다.
                ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory() {
                    // thread factory 커스텀
                    @Override
                    public String getThreadNamePrefix() {
                        return "pubOn-";
                    }
                });

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
//                    sub.onNext(integer);
                    es.execute(() -> sub.onNext(integer));
                }

                @Override
                public void onError(Throwable t) {
//                    sub.onError(t);
                    es.execute(() -> sub.onError(t));
                    es.shutdown(); // 스레드풀 종료
                }

                @Override
                public void onComplete() {
//                    sub.onComplete();
                    es.execute(() -> sub.onComplete());
                    es.shutdown();
                }
            });
        };

        // sub
        pubOnPub.subscribe(new Subscriber<Integer>() {
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
