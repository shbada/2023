package com.reactive.step02;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class E07_ReducePub {
    public static void main(String[] args) {
        Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));

        // 덧셈, 곱셈 등 어떤 계산을 수행할지는 정하겠다.
        // BiFunction<Integer, Integer, Integer> : a 타입, b 타입, 최종 결과 타입
        Publisher<Integer> sumPup = reducePub(pub, 0, (BiFunction<Integer, Integer, Integer>) (a, b) -> a + b); // 초기데이터 , 함수 전달

        // 구독 시작
        sumPup.subscribe(logSub());
    }

    // 1, 2, 3, 4, 5
    // 0 -> (0, 1) -> 0 + 1 = 1
    // 1 -> (1, 2) -> 1 + 2 = 3
    // 2 -> (3, 3) -> 3 + 3 = 6
    // ...
    private static Publisher<Integer> reducePub(Publisher<Integer> pub, int init, BiFunction<Integer, Integer, Integer> bf) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {

                pub.subscribe(new DelegateSub(sub) {
                    int result = init;

                    @Override
                    public void onNext(Integer i) {
                        result = bf.apply(result, i);
                    }

                    @Override
                    public void onComplete() {
                        sub.onNext(result);

                        sub.onComplete();
                    }
                });
            }
        };
    }

    private static Subscriber<Integer> logSub() {
        Subscriber<Integer> sub = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                // Subscription 의 request 를 요청해야한다.
                log.debug("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer i) {
                log.debug("onNext:{}", i);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError:{}", t);
            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        };
        return sub;
    }

    private static Publisher<Integer> iterPub(List<Integer> iter) {
        Publisher<Integer> pub = new Publisher<Integer>() {
            // Publisher 의 구현해야하는 메서드
            @Override
            public void subscribe(Subscriber<? super Integer> sub) { // 호출하면 그때부터 데이터를 통지
                // Subscription : Publisher, Subscriber 둘 사이의 구독이 한번 일어난다는 의미
                sub.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            // iterable 의 원소를 통지한다.
                            iter.forEach(s -> sub.onNext(s));
                            // 여기서 멈추면 안되고, publisher 가 데이터 통지가 완료했으면 완료됨을 호출해야한다.
                            sub.onComplete();
                        } catch (Throwable t) {
                            // 에러 처리
                            sub.onError(t);
                        }
                    }

                    /**
                     * Subscriber 에서 Subscription 객체의 cancel()을 호출할 수 있다.
                     * 더이상 데이터를 통지받지 않겠다고 알림
                     */
                    @Override
                    public void cancel() {

                    }
                });
            }
        };
        return pub;
    }
}
