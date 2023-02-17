package com.concurrency.chapter07.예제7_09_작업실행전용스레드에_인터럽트걸기;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.concurrency.chapter05.예제5_12_FutureTask_필요한데이터_미리_읽기.LaunderThrowable.launderThrowable;
import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * 작업을 실행하도록 생성한 스레드에는 적절한 실행 정책을 따로 정의할 수도 있고,
 * 작업이 인터럽트에 응답하지 않는다 해도 시간이 제한된 메서드 자체는 호출한 메서드에게 리턴된다.
 *
 * timedRun 메서드는 작업 실행 스레드를 실행한 다음, 실행 스레드를 대상으로 시간 제한이 설정된 join 메서드를 호출한다.
 * join 메서드가 리턴되고나면 timedRun 메서드에서는 먼저 작업을 실행하는 과정에 발생한 예외가 있는지 확인하고,
 * 예외가 발생했었다면 해당 예외를 상위 메서드에게 다시 던진다.
 * Throwable 클래스는 일단 저장해두고 호출 스레드와 작업 스레드가 서로 고유하는데,
 * 예외를 작업 스레드에서 호출 스레드로 안전하게 공개할 수 있도록 volatile로 선언한다.
 *
 * 예제 7_7의 문제점 해결 - join 메서드 사용
 * [join 메서드 사용의 단점]
 * - timedRun 메서드가 리턴됐을때 정상적으로 스레드가 종료된 것인지 join 메서드에서 타임아웃이 걸린 것인지를 알수 없다.
 *
 */

/**
 * TimedRun2
 * <p/>
 * Interrupting a task in a dedicated thread
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun2 {
    private static final ScheduledExecutorService cancelExec = newScheduledThreadPool(1);

    public static void timedRun(final Runnable r,
                                long timeout, TimeUnit unit)
            throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t; // volatile

            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null)
                    throw launderThrowable(t);
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);

        // 실행 스레드를 대상으로 시간 제한이 설정된 join 메서드
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }
}