package com.concurrency.chapter13.예제13_04_일정시간_이내에_락을확보;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * TimedLocking
 * <p/>
 * Locking with a time budget
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedLocking {
    private Lock lock = new ReentrantLock();

    public boolean trySendOnSharedLine(String message,
                                       long timeout, TimeUnit unit)
            throws InterruptedException {
        long nanosToLock = unit.toNanos(timeout) - estimatedNanosToSend(message);

        /* 일정 시간 이내에 락을 확보하는 모습 */
        if (!lock.tryLock(nanosToLock, NANOSECONDS))
            return false;
        try {
            return sendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }

    private boolean sendOnSharedLine(String message) {
        /* send something */
        return true;
    }

    long estimatedNanosToSend(String message) {
        return message.length();
    }
}
