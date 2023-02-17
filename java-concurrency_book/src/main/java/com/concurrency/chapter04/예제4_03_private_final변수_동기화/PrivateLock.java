package com.concurrency.chapter04.예제4_03_private_final변수_동기화;

import net.jcip.annotations.*;

/**
 * PrivateLock
 * <p/>
 * Guarding state with a private lock
 *
 * @author Brian Goetz and Tim Peierls
 */
public class PrivateLock {
    /*
        락으로 활용하기 위한 private 객체
        private으로 선언되어있으므로 외부에서 락을 건드릴 수 없다.
        만약 락이 객체 외부에 공개되어 있다면 다른 객체도 해당 락을 활용해 동기화 기법에 참여할 수 있게된다. (잘못될 수도 있다.)
     */
    private final Object myLock = new Object();
    @GuardedBy("myLock") Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // Access or modify the state of widget
        }
    }
}

class Widget {

}