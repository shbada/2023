package com.reactive.step03;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class E15_IntervalEx {
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                int no = 0;
                boolean cancelled = false;

                @Override
                public void request(long n) {
                    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
                    exec.scheduleAtFixedRate(() -> {
                        if (cancelled) {
                            exec.shutdown();
                            return;
                        }

                        sub.onNext(no++); // 구독자에게 통지한다.
                    }, 0, 300, TimeUnit.MICROSECONDS);
                }

                @Override
                public void cancel() {
                    cancelled = true;
                }
            });
        };

        Publisher<Integer> takePub = sub -> {
          pub.subscribe(new Subscriber<Integer>() {
              int count = 0;
              Subscription subsc;

              @Override
              public void onSubscribe(Subscription s) {
                  subsc = s;
                  sub.onSubscribe(s);
              }

              @Override
              public void onNext(Integer integer) {
                  if (++count > 4) { // 10번넘에 통지했으면 더이상 안되도록 설정해보자.
                      subsc.cancel();
                  }

                  sub.onNext(integer);
              }

              @Override
              public void onError(Throwable t) {
                  sub.onError(t);
              }

              @Override
              public void onComplete() {
                  sub.onComplete();
              }
          });
        };

        takePub.subscribe(new Subscriber<Integer>() {
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
    }
}
