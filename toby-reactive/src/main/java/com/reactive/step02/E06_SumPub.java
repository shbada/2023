package com.reactive.step02;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class E06_SumPub {
    public static void main(String[] args) {
        Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));

        // sum : 데이터가 날라와도 logSub에 다 던지는게 아닌 계산을 계속 진행하다가, 합계를 전부 계산 완료했을때 logSub에 던진다.
        Publisher<Integer> sumPup = sumPub(pub);

        // 구독 시작
        sumPup.subscribe(logSub());
    }

    private static Publisher<Integer> sumPub(Publisher<Integer> pub) {
        return new Publisher<Integer>() {
            // 중개 역할
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                pub.subscribe(new DelegateSub(sub) {
                    int sum = 0;
                    @Override
                    public void onNext(Integer i) {
                        // 넘기면 안된다.
                        sum += i;

                        // 결과를 넘기는건 모든 데이터의 합계를 완료했을 때
                        // 완료를 알 수 있는 법 : onComplete()
                    }

                    /**
                     * Publisher가 완료의 신호를 줄때, 그때 Sub의 onNext를 호출해서 데이터를 한번 전달한다.
                     */
                    @Override
                    public void onComplete() {
                        sub.onNext(sum);
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
