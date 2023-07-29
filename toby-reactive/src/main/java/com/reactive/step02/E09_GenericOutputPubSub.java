package com.reactive.step02;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class E09_GenericOutputPubSub {
    public static void main(String[] args) {
        Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));

        // 2단계 적용
        Publisher<String > mapPub = mapPub(pub, (Function<Integer, String>) s -> "[" + s + "]");

        // 구독 시작
        mapPub.subscribe(logSub());
    }

    /**
     * 중간 연결
     * T -> R
     * @param pub
     * @param f
     * @return
     */
    private static <T, R> Publisher<R> mapPub(Publisher<T> pub, Function<T, R> f) {
        return new Publisher<R>() {
            // pub -> data1 -> mapPub(Subscriber 생성) -> data2 -> logSub
            @Override
            public void subscribe(Subscriber<? super R> sub) { // logSub
                pub.subscribe(new E09_DelegateGenericOutputSub<T, R>(sub) {
                    @Override
                    public void onNext(T i) {
                        sub.onNext(f.apply(i));
                    }
                }); // 새로운 Subscriber
            }
        };
    }

    private static <T> Subscriber<T> logSub() {
        return new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription s) {
                // Subscription 의 request 를 요청해야한다.
                log.debug("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(T i) {
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
