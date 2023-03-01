package com.concurrency.chapter08.예제8_09_ThreadPoolExecutor_상속하여_로그_시간_측정;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.logging.*;

/**
 * TimingThreadPool
 * <p/>
 * Thread pool extended with logging and timing
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimingThreadPool extends ThreadPoolExecutor {

    public TimingThreadPool() {
        super(1, 1, 0L, TimeUnit.SECONDS, null);
    }

    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final Logger log = Logger.getLogger("TimingThreadPool");
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    /**
     * 시작 시간을 기록한다.
     * 여기서 RuntimeException이 발생하면 해당 작업도 실행되지 않을 뿐더러,
     * afterExecute 메서드 역시 실행되지 않으니 주의하자.
     * @param t the thread that will run task {@code r}
     * @param r the task that will be executed
     */
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.fine(String.format("Thread %s: start %s", t, r));

        startTime.set(System.nanoTime());
    }

    /**
     * 시작할때 기록해뒀던 시간을 참조해 작업이 실행된 시간을 알아낸다.
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     * execution completed normally
     */
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();

            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);

            log.fine(String.format("Thread %s: end %s, time=%dns",
                    t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    protected void terminated() {
        try {
            log.info(String.format("Terminated: avg time=%dns",
                    totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
        }
    }
}