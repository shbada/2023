package com.concurrency.chapter05.예제5_16부터_예제5_18_캐시구현.예제5_19_캐시구현_최종버전;

import com.concurrency.chapter05.예제5_12_FutureTask_필요한데이터_미리_읽기.LaunderThrowable;

import java.util.concurrent.*;


/**
 [최종단계. Future > putIfAbsent() 단일 연산 메서드 사용]
 실제 결과값 대신 Future 객체를 캐시한다.
 특정 시점에 시도했던 연산이 취소되거나 오류가 발생했었다면 Future 객체 역시 취소되거나 오류가 발생했던 상황을 알려줄 것이다.
 이런 문제를 해결하기 위해 연산이 취소되는 경우에는 캐시에서 해당하는 Future 객체를 제거한다.

 캐시된 내용이 만료되는 기능?
 - 이부분은 FutureTask 클래스를 상속받아 만료된 결과인지 여부를 알 수 있는 새로운 클래스를 만들어 사용하고,
 결과 캐시를 주기적으로 돌아다니면서 만료된 결과 항목이 있는지 조사해 제거하는 기능을 구현하면 된다.

 */

/**
 * Memoizer
 * <p/>
 * Final implementation of Memoizer
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer <A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache
            = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    public V call() throws InterruptedException {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                /** putIfAbsent() 사용
                 *  putIfAbsent 메서드는 put과 exist 확인을 단일 연산 처럼 사용하는 메서드이다.
                 *  값이 없으면, 넣는 메서드인데 이를 하나의 synchronized 로 묶은 것 처럼, atomic 을 보장하게 해주는 메서드라는 의미다.
                 * */
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw LaunderThrowable.launderThrowable(e.getCause());
            }
        }
    }
}

interface Computable <A, V> {
    V compute(A arg) throws InterruptedException;
}