package com.concurrency.chapter08.예제8_06_직접작성한_스레드팩토리;

import java.util.concurrent.*;

/**
 * ThreadFactory를 상속하여 직접 작성해보기
 */

/**
 * MyThreadFactory
 * <p/>
 * Custom thread factory
 *
 * @author Brian Goetz and Tim Peierls
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    public Thread newThread(Runnable runnable) {
        return new MyAppThread(runnable, poolName);
    }
}
