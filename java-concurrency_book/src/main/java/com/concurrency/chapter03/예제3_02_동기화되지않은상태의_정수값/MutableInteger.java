package com.concurrency.chapter03.예제3_02_동기화되지않은상태의_정수값;

import net.jcip.annotations.*;

/**
 * MutableInteger
 * <p/>
 * Non-thread-safe mutable integer holder
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class MutableInteger {
    private int value;

    /**
     * 동기화가 되어있지 않으므로 스테일 현상 발생 가능성이 높다.
     * 스테일 : 읽기 스레드가 변수의 값을 읽어올때, 해당 변수의 최신 값이 아닌 경우
     * @return
     */
    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}
