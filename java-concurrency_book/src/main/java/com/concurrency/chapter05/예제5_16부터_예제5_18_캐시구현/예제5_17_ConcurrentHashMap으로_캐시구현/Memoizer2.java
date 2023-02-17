package com.concurrency.chapter05.예제5_16부터_예제5_18_캐시구현.예제5_17_ConcurrentHashMap으로_캐시구현;

import java.util.*;
import java.util.concurrent.*;

/**
 [2단계. ConcurrentHashMap 사용]
 ConcurrentHashMap은 이미 스레드 안정성을 확보하고 있기 때문에 그 내부의 Map을 사용할때 별다른 동기화 방법을 사용하지 않아도 된다.
 여러개의 스레드가 Memoizer2를 실제로 동시다발적으로 마음껏 사용할 수 있다.

 - 미흡한 부분
 두개 이상의 스레드가 동시에 같은 값을 넘기면서 compute 메서드를 호출해 같은 결과를 받아갈 가능성이 있다.
 메모이제이션이라는 측면에서 보면 이런 상황은 단순히 효율성이 약간 떨어지는것 뿐이다.
 캐시는 같은 값으로 같은 결과를 연산하는 일을 두번이상 실행하지 않겠다는 것이기 때문이다.
 훨씬 일반적인 형태의 캐시 메커니즘을 생각한다면 좀더 안좋은 상황을 생각해야한다.
 캐시할 객체를 한번만 생성해야하는 객체 캐시의 경우에는 똑같은 결과를 두개 이상 만들어낼 수 있는 이런 문제점이 안정성 문제로 이어질 수 있다.

 - 문제
 특정 스레드가 compute 메서드에서 연산을 시작했을때, 다른 스레드는 현재 어떤 연산이 이뤄지고 있는지 알수가 없다.
 그렇기에 동일한 연산을 시작할 수 있다.

 */

/**
 * Memoizer2
 * <p/>
 * Replacing HashMap with ConcurrentHashMap
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer2 <A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(A arg) throws InterruptedException {
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