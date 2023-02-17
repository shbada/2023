package com.concurrency.chapter01;

import net.jcip.annotations.GuardedBy;

/**
 * 스레드 안정성 문제를 신경써야한다.
 */
public class _02_Sequence {
    @GuardedBy("this") private int value;

    /**
     * 동기화 메서드로 수정
     * @return
     */
    public synchronized int getNext() {
        return value++;
    }
}
