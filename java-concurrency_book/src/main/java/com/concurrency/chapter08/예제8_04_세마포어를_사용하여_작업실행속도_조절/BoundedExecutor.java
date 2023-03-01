package com.concurrency.chapter08.예제8_04_세마포어를_사용하여_작업실행속도_조절;

import java.util.concurrent.*;

import net.jcip.annotations.*;

/**
 * BoundedExecutor
 * <p/>
 * Using a Semaphore to throttle task submission
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command)
            throws InterruptedException {
        /*
           남는 permit이 생기거나, 인터럽트가 걸리거나, 지정한 시간을 넘겨 타임아웃이 걸리기 전까지 대기한다.
         */
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
            /*
               기본적으로 사용되는 '집중 대응 중단 정책'은 중단 정책이며,
               execute() 에서 RuntimeException을 상속받은 RejectedExecutionException을 던진다.
               execute()를 호출하는 스레드는 RejectedExecutionException을 잡아서 작업을 더이상 추가할 수 없는 상황에 직접 대응해야한다.
             */
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }
}
