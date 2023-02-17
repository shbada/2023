package com.concurrency.chapter03.예제3_03_동기화된상태의_정수값;

import net.jcip.annotations.*;

/**
 * SynchronizedInteger
 * <p/>
 * Thread-safe mutable integer holder
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SynchronizedInteger {
    @GuardedBy("this") private int value;

    /**
     * 동기화!!
     * get, set 모두 동기화를 해야 의미가 있다.
     * get은 안하고 set 메서드만 하면 get 메서드가 여전히 스테일 상황을 초래할 수 있다.
     * @return
     */
    public synchronized int get() {
        return value;
    }

    public synchronized void set(int value) {
        this.value = value;
    }
}

