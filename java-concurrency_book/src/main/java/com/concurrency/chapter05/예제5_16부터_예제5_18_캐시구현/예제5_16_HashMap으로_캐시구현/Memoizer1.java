package com.concurrency.chapter05.예제5_16부터_예제5_18_캐시구현.예제5_16_HashMap으로_캐시구현;

import java.math.BigInteger;
import java.util.*;

import net.jcip.annotations.*;

/**
 [1단계. HashMap 사용]

 Hashmap은 스레드아 안전하지 않기 때문에 Memoizer1은 두개 이상의 스레드가 Hashmap에 동시에 접근하지 못하도록
 compute 메서드 전체를 동기화했다.
 이 방법은 스레드 안정성은 쉽게 확보하지만, 확장성 측면에서 문제가 생긴다.
 특정 시점에 여러 스레드 가운데 하나만이 compute 메서드를 실행할 수 있기 때문이다.
 스레드 하나가 compute 메서드를 실행했는데 연산 시간이 오래걸린다면, compute 메서드를 실행하고자 대기하고 있는 다른 스레드는 상당한 시간을 기다려야한다.
 여러개의 스레드가 compute를 호출하려고 대기하고 있다면 훨씬 성능이 낮아질 수 있다.
 */

/**
 * Memoizer1
 *
 * Initial cache attempt using HashMap and synchronization
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer1 <A, V> implements Computable<A, V> {
    @GuardedBy("this") private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}


interface Computable <A, V> {
    V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction
        implements Computable<String, BigInteger> {
    public BigInteger compute(String arg) {
        // after deep thought...
        return new BigInteger(arg);
    }
}

