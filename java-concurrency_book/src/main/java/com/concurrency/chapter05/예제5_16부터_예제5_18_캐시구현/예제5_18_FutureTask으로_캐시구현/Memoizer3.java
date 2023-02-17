package com.concurrency.chapter05.예제5_16부터_예제5_18_캐시구현.예제5_18_FutureTask으로_캐시구현;

import com.concurrency.chapter05.예제5_12_FutureTask_필요한데이터_미리_읽기.LaunderThrowable;

import java.util.*;
import java.util.concurrent.*;

/**
 [3단계. FutureTask]
 FutureTask는 이미 끝났거나 끝날 예정인 연산 작업을 표현한다.
 get()은 연산 작업이 끝나는 즉시 연산 결과를 리턴한다.
 만약 결과를 연산하는 도중이라면 작업이 끝날때까지 기다렸다가 그 결과를 알려준다.

 Memoizer3는 Map의 기존의 ConcurrentHashMap<A, V> 대신 ConcurrentHashMap<A, Future<V>>라고 정의했다.

 (예제)
 결과를 이미 알고있다면 계산 과정을 거치지 않고 즉시 가져갈 수 있고,
 특정 스레드가 연산 작업을 진행하고 있다면 뒤이어 오는 스레드는 진행중인 연산 작업의 결과를 기다린다.

 - 문제
 허점은 있다.
 여전히 여러 스레드가 같은 값에 대한 연산을 시작할 수 있다.
 compute 메서드의 if문이 비교하고 동작하는 두 단계의 연산이기 때문에,
 여러 스레드가 compute 메서드의 if문을 거의 동시에 실행한다면 모두 계산된 값이 없다고 판단하고 새로운 연산을 시작한다.

 */

/**
 * Memoizer3
 * <p/>
 * Memoizing wrapper using FutureTask
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer3 <A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache
            = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        /** 원하는 값이 완료됬는지 확인한다. */
        Future<V> f = cache.get(arg);
        if (f == null) { // 작업이 없다면 시작한다.
            Callable<V> eval = new Callable<V>() {
                public V call() throws InterruptedException {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run(); // call to c.compute happens here
        }
        try {
            /** 작업이 있다면 결과를 기다린다. */
            return f.get();
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e.getCause());
        }
    }
}

interface Computable <A, V> {
    V compute(A arg) throws InterruptedException;
}