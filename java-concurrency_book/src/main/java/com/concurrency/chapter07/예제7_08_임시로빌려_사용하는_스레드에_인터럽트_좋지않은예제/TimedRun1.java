package com.concurrency.chapter07.예제7_08_임시로빌려_사용하는_스레드에_인터럽트_좋지않은예제;

import java.util.concurrent.*;

/**
 * [예제 : 시간 지정 실행] - 임시로 빌려 사용하는 스레드에 인터럽트 거는 방법, 이런 코드는 금물!
 *
 * Runnable을 구현한 임의의 작업을 일정 시간 동안만 실행하도록 작성된 코드다.
 * 실제 작업을 호출하는 스레드 내부에서 실행시키고,
 * 일정 시간이 지난 이후에 인터럽트를 걸도록 되어있는 작업 중단용 스레드를 따로 실행시킨다.
 *
 * 이렇게 구현하면 timedRun 메서드를 호출한 메서드의 catch 구문에서 예외를 잡기 때문에
 * 작업을 실행하는 도중에 확인되지 않은 예외가 발생하는 상황에 대응할 수 있다.
 *
 * 스레드에 인터럽트를 걸때 대상 스레드의 인터럽트 정책을 알고 있어야 한다는 규칙을 어기고 있다.
 * timedRun 메서드는 외부의 어떤 스레드에서도 호출할 수 있게되어 있는데,
 * 호출하는 스레드의 인터럽트 정책은 알 수가 없기 때문이다.
 * 지정한 시간이 다 되기전에 작업이 끝난다면 timedRun 메서드를 호출했던 스레드는 timedRun 메서드 실행을
 * 모두 끝 마치고, 그 다음 작업을 실행하고 있을텐데, 작업 중단 스레드는 다음 작업을 실행하는 도중에 인터럽트를 걸게 된다.
 * 인터럽트가 걸리는 시점에 어떤 코드가 실행되고 있을지는 전혀 알 수 없지만, 결과가 정상적이지 않을 것이라는 점을 쉽게 예측할 수 있다.
 *
 * 더군다나 작업 내부가 인터럽트에 제대로 반응하지 않도록 만들어져있다면,
 * timedRun 메서드는 작업이 끝날때까지 리턴되지 않고 계속 실행될 것이며, 그러다보면 지정된 실행시간을 훨씬 넘겨버릴 가능성이 많다.
 * 일정 시간 동안만 실행하라고 메서드를 호출했는데, 지정된 시간 이상 실행되면 원래 스레드의 입장에서는 문제가 될 수도 있는 상황이다.
 */

/**
 * InterruptBorrowedThread
 * <p/>
 * Scheduling an interrupt on a borrowed thread
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun1 {
    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable r,
                                long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(new Runnable() {
            // 일정 시간이 지난 이후에 인터럽트를 걸도록 되어있는 작업 중단용 스레드를 따로 실행
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);
        r.run();
    }
}
