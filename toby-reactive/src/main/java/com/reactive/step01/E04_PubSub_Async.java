package com.reactive.step01;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class E04_PubSub_Async {
    public static void main(String[] args) throws InterruptedException {
        // ReactiveX (RxJava, RxJS 등)

        // Publisher (연속된 요소들을 제공) <- Observable
        // Subscriber (정보를 요청하고, Publisher 로부터 데이터를 받는다.) <- Observer
        // Publisher.subscribe(Subscriber)
        // -> signal은 Subscriber의 onSubscribe(), onNext, onError, onComplete

        // Publisher 를 만들어보자.
        List<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);

        // 별도 스레드
        ExecutorService es = Executors.newSingleThreadExecutor();

        // 위 데이터를 던지는 Publisher
        Flow.Publisher p = new Flow.Publisher() {
            // 구독 방식
            @Override
            public void subscribe(Flow.Subscriber subscriber) {
                // onSubscribe()는 반드시 호출되어야한다.
                // Subscriber -> (Demend) Subscription(중개역할) -> Publisher ->(이벤트) Subscriber
                subscriber.onSubscribe(new Flow.Subscription() { // Subscriber.onSubscribe() 호출
                    Iterator<Integer> it = itr.iterator();

                    /**
                        [비동기적으로 구현]
                        Publisher 가 쓰레드를 10개만들어서 데이터 통지를 한다면?
                        스펙상으로 불가능하다.
                        하나의 Subscriber는 순서대로 데이터가 통지됨으로 알고있다.
                        어느 한순간에는 적어도 한 스레드에서만 데이터가 날라올 수 있다고 본다.

                        동시에 다른 스레드에서 멀티스레드 방식으로 동작하는건 가능하다.
                     */
                    @Override
                    public void request(long n) {
//                        es.execute(() -> {
//                            try {
//                                // Subscriber.onSubscribe() 안에서 호출
//                                while (n-- > 0) {
//                                    if (it.hasNext()) {
//                                        subscriber.onNext(it.next()); // 데이터 통지
//                                    } else {
//                                        subscriber.onComplete(); // 통지 완료
//                                        break;
//                                    }
//                                }
//                            } catch (RuntimeException e) {
//                                // 에러 발생시 Subscriber 에서 처리
//                                subscriber.onError(e);
//                            }
//                        });

                        // 결과값을 받고 싶을 경우
                        // Future : 자바 비동기 작업의 결과 정보를 제공해주는 클래스
                        Future<?> submit = es.submit(() -> {
                            // 스레드 한정 위반 - 해결
                            int i = 0;

                            try {
                                // Subscriber.onSubscribe() 안에서 호출
                                while (i++ < n) { // 스레드 한정 위반
                                    if (it.hasNext()) {
                                        subscriber.onNext(it.next()); // 데이터 통지
                                    } else {
                                        subscriber.onComplete(); // 통지 완료
                                        break;
                                    }
                                }
                            } catch (RuntimeException e) {
                                // 에러 발생시 Subscriber 에서 처리
                                subscriber.onError(e);
                            }
                        });
                    }

                    @Override
                    public void cancel() { // 작업을 취소시킬 수 있다. 이때 Future를 통해서 interrupt를 날릴 수 있다.

                    }
                });
            }
        };

        // Subscriber 를 만들어보자.
        Flow.Subscriber<Integer> s  = new Flow.Subscriber<Integer>() {
            Flow.Subscription subscription;


            /*
                subscribe()한 동일한 쓰레드 내에서 처리되야한다.
             */
            @Override
            public void onSubscribe(Flow.Subscription subscription) { // 필수
                System.out.println(Thread.currentThread().getName() + " : E03_PubSub.onSubscribe");

                // subscription 저장
                this.subscription = subscription;

                // subscription의 reuqest()
//                subscription.request(Long.MAX_VALUE); // 전부 다 받기

                // 이것도 동일한 쓰레드 내에서 처리되야한다. (새로운 스레드 만들어서 request 날리면 안된다.)
                this.subscription.request(1); // 1개 받기

                System.out.println(Thread.currentThread().getName() + " : E03_PubSub.onSubscribe");
            }

            int bufferSize = 2;

            /**
             * publisher 에서 통지한 데이터를 처리
             * @param item the item
             */
            @Override
            public void onNext(Integer item) {
                System.out.println(Thread.currentThread().getName() + " : E03_PubSub.onNext : " + item);

                // 다음 데이터를 다시 요청
                if (--bufferSize <= 0) {
                    bufferSize = 2;
                    this.subscription.request(2); // 2개 받기
                }
            }

            /**
             * 오류 처리 (Publisher 에서 어떤 종류의 에러가 발생하더라도 이 메서드에서 처리한다.)
             * @param throwable the exception
             */
            @Override
            public void onError(Throwable throwable) {
                System.out.println("E03_PubSub.onError : " + throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("E03_PubSub.onComplete");
            }
        };

        // 구독
        p.subscribe(s);

        // time 걸어서 동작중에 shutdown 되지 않도록 한다.
        es.awaitTermination(10, TimeUnit.HOURS);
        es.shutdown();
    }
}
