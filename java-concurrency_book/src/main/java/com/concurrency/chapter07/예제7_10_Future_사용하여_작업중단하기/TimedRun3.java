package com.concurrency.chapter07.예제7_10_Future_사용하여_작업중단하기;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.concurrency.chapter05.예제5_12_FutureTask_필요한데이터_미리_읽기.LaunderThrowable.launderThrowable;

/**
 * TimedRun
 * <p/>
 * Cancelling a task using Future
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun3 {
    /* ExecutorService 를 통해 실행 */
    private static final ExecutorService taskExec = Executors.newCachedThreadPool();

    public static void timedRun(Runnable r,
                                long timeout, TimeUnit unit)
            throws InterruptedException {
        Future<?> task = taskExec.submit(r); // Future 인스턴스로 리턴

        try {
            // get 메서드가 TimeoutException을 띄우면서 멈췄다면 해당 작업은 Future를 통해 작업이 중단된 것이라고 볼 수 있다.
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // task will be cancelled below
        } catch (ExecutionException e) {
            // exception thrown in task; rethrow
            throw launderThrowable(e.getCause());
        } finally {
            // Harmless if task already completed
            // cancel() 이 boolean 값을 파라미터로 받는다.
            // 취소 요청에 따른 작업 중단 시도가 성공적이었는지를 알려주는 결과 값을 리턴받을 수 있다.
            // 작업 중단 시도가 성공적이었다는 의미는 인터럽트를 제대로 걸었다는 의미이고, 해당 작업이 인터럽트에 반응하여 실제로 작업을 중단했다는 것을 뜻하지는 않는다.
            // true를 넘겼고, 작업이 어느 스레드에서건 실행되고 있었다면, 해당 스레드에 인터럽트가 걸린다.
            // false 를 넘겨주면 "아직 실행하지 않았다면 실행시키지 말아라"는 의미로, 인터럽트에 대응하도록 만들어지니않은 작업에는 false를 넘겨야한다.

            // true를 사용한 이유는?
            // Exceutor에서 기본적으로 작업을 실행하기 위해 생성하는 스레드는 인터럽트가 걸렸을때 작업을 중단할 수 있도록 하는 인터럽트 정책을 사용한다.
            // 스레드에 인터럽트가 걸리는 시점에 어떤 작업을 실행하고 있는지 알 수 없으므로, 작업을 중단하려할 때는 항상 스레드에 인터럽트를 거는 대신 Future의 cancel 메서드를 사용해야한다.
            // 작업을 구현할때 인터럽트가 걸리면 작업을 중단하라는 요청으로 해석하고 그에 따라 행동하도록 만들어야하는 또 다른 이유인데, 이는 Future를 통해 쉽게 작업 중단이 가능하기 때문이다.
            task.cancel(true); // interrupt if running (실행중이라면 인터럽트를 건다)
            // 작업이 정상적으로 종료된 후 finally 실행하여 cancel()이 호출되도 아무런 이상이 없기 때문에 문제없이 동작한다.
        }
    }
}

