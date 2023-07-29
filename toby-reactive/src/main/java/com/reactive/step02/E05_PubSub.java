package com.reactive.step02;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Publisher -> [Data1] -> Opreator -> [Data2(Data1 변환)] -> Opration2 -> [Data3(Data2 변환)] -> Subscriber
 * 1) map (D1 -> f -> D2)
 */
@Slf4j
public class E05_PubSub {
    public static void main(String[] args) {
        // pub -> data1 -> mapPub -> data2 -> logSub (위에서 아래로 데이터가 흐른다 : 다운스트림)
        //              <- subscribe(logSub) (아래에서 위로 데이터가 흐른다 : 업스트림)
        //              -> onSubscribe(s)
        //              -> onNext
        //              -> onNext
        //              -> onComplete
        Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));

        // 2단계 적용
        Publisher<Integer> mapPub = mapPub(pub, (Function<Integer, Integer>) s -> s * 10);
        Publisher<Integer> map2Pub = mapPub(pub, (Function<Integer, Integer>) s -> -s);

        // 구독자
        Subscriber<Integer> sub = logSub();

        // 구독 시작
//        pub.subscribe(sub);
        mapPub.subscribe(sub);
//        map2Pub.subscribe(sub);
    }

    /**
     * 중간 연결
     * @param pub
     * @param f
     * @return
     */
    private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> f) {
        return new Publisher<Integer>() {
            // pub -> data1 -> mapPub(Subscriber 생성) -> data2 -> logSub
            @Override
            public void subscribe(Subscriber<? super Integer> sub) { // logSub
//                pub.subscribe(sub);
                pub.subscribe(new DelegateSub(sub) {
                    @Override
                    public void onNext(Integer i) {
                        sub.onNext(f.apply(i));
                    }
                }); // 새로운 Subscriber
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
