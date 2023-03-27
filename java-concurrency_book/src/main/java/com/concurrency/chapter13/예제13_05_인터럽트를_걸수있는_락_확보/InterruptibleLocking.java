package com.concurrency.chapter13.예제13_05_인터럽트를_걸수있는_락_확보;

import java.util.concurrent.locks.*;

/**
 * InterruptibleLocking
 *
 * @author Brian Goetz and Tim Peierls
 */
public class InterruptibleLocking {
    private Lock lock = new ReentrantLock();

    public boolean sendOnSharedLine(String message) throws InterruptedException {
        lock.lockInterruptibly(); // 취소 가능한 작업으로 실행
        /*
        타임아웃을 지정하는 tryLock 메서드 역시 인터럽트를 걸면 반응하도록 돼있으며,
        인터럽트를 걸어 취소시킬 수도 있어야 하면서 동시에 타임아웃을 지정할 수 있어야한다면,
        tryLock을 사용하는 것만으로도 충분하다.
         */

        try {
            return cancellableSendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }

    private boolean cancellableSendOnSharedLine(String message) throws InterruptedException {
        /* send something */
        return true;
    }

}
