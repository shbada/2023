package com.concurrency.chapter11.예제11_08_락_스트라이핑_사용하는_해시_기반_맵;

import net.jcip.annotations.*;

/**
 * (어려운 내용..)
 *
 * 락 스트라이핑(lock striping)
 * = 락 분할 방법을 때에 따라 독립적인 객체를 여러 가지 크기의 단위로 묶어내고,
 * 묶인 블록을 단위로 락을 나누는 방법을 사용할 수도 있다.
 *
 * 락 스트라이핑을 사용하다보면 여러개의 락을 사용하도록 쪼개놓은 컬렉션 전체를
 * 한꺼번에 독립적으로 사용해야할 필요가 있을 수 있는데,
 * 이런 경우에는 단일 락을 사용할때보다 동기화시키기가 어렵고 자원도 많이 소모한다는 단점이 있다.
 * 이런 경우에는 보통 쪼개진 락을 전부 확보한 이후에 처리하도록 구현한다.
 *
 * 아래 예제.
 * 락 스트라이핑을 사용하는 해시 기반의 맵 클래스
 * N_LOCKS 만큼의 락을 생성하고, N_LOCKS개의 락이 각자의 범위에 해당하는 해시 공간에 대한 동기화를 담당한다.
 */

/**
 * StripedMap
 * <p/>
 * Hash-based map using lock striping
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class StripedMap {
    // Synchronization policy: buckets[n] guarded by locks[n%N_LOCKS]
    private static final int N_LOCKS = 16;
    private final Node[] buckets;
    private final Object[] locks;

    private static class Node {
        Node next;
        Object key;
        Object value;
    }

    public StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];
        for (int i = 0; i < N_LOCKS; i++)
            locks[i] = new Object();
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node m = buckets[hash]; m != null; m = m.next)
                if (m.key.equals(key))
                    return m.value;
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]) {
                buckets[i] = null;
            }
        }
    }
}
