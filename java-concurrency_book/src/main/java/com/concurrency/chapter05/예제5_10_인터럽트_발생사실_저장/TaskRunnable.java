package com.concurrency.chapter05.예제5_10_인터럽트_발생사실_저장;

/**
 스레드는 여러가지 원인에 의해 블록 당하거나 멈춰질 수 있다.
 예를들어 I/O 작업이 끝나기를 기다리는 경우도 있고, 락을 확보하기 위해 기다리는 경우도 있다.
 Thread.sleep 메서드가 끝나기를 기다리는 경우도 있고, 다른 스레드가 작업중인 내용의 결과를 확인하기 위해 기다리는 경우도 있다.
 스레드가 블록되면 동작이 멈춰진 다음, 블록된 상태 (BLOCKED, WAITING< TIMES_WAITING) 가운데 하나를 갖게된다.
 블로킹 연산은 멈춘 상태에서 특정한 신호를 받아야 계속해서 실행할 수 있는 연산이다.
 기다리던 외부 신호가 확인되면 스레드의 상태가 다시 RUNNABLE 상태로 넘어가고, 다시 시스템 스케줄러를 통해 CPU를 사용할 수 있게된다.

 특정 메서드가 InterruptedException 을 발생시킬 수 있다는 것은 해당 메서드가 블로킹 메서드라는 의미이고,
 만약 메서드에 인터럽트가 걸리면 해당 메서드는 대기 중인 상태에서 풀려나고자 노력한다.

 Thread 클래스는 해당 스레드를 중단시킬 수 있도록 interrupt 메서드를 제공하며,
 해당 스레드에 인터럽트가 걸려 중단된 상태인지를 확인할 수 있는 메서드도 있다.
 모든 스레드에는 인터럽트가 걸린 상태인지를 알려주는 boolean 값이 있으며, 외부에서 인터럽트를 걸면 불린 변수에 true가 설정된다.
 인터럽트는 스레드가 서로 협력해서 실행하기 위한 방법이다.
 어떤 스레드라도 다른 스레드가 하고있는 일은 중간에 강제로 멈추라고 할 수는 없다.
 인터럽트를 건다는것은 단순히 실행을 멈추라고 '요청'하는 것일 뿐, 인터럽트가 걸린 스레드는 정상적인 종료 시점 이전에 적절한 때에 작업을 멈추면 된다.
 일반적으로 인터럽트는 특정 작업을 중간에 멈추게 하려는 경우에 사용한다.
 
 InterruptedException 대처 방법
 1) InterruptedException 전달
 : 받아낸 InterruptedException 을 그대로 호출한 메서드에게 넘긴다. 
 
 2) 인터럽트를 무시하고 복구
 : 특정 상황에서는 InterruptedException을 throw할 수 없을 수가 있다. 이런 경우에는 catch한 다음, 현재 스레드의 interrypt 메서드를 호출해서
 인터럽트 상태를 설정해 상위 호출 메서드가 인터럽트 상황이 발생했음을 알 수 있도록 해야한다.

 InterruptedException 를 catch 하고는 무시하고 아무 대응도 하지 않는 일은 없어야한다.
 */
import java.util.concurrent.*;

/**
 * TaskRunnable
 * <p/>
 * Restoring the interrupted status so as not to swallow the interrupt
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;

    public void run() {
        try {
            // 블로킹 메서드 수행
            processTask(queue.take());
        } catch (InterruptedException e) {
            // 인터럽트가 발생한 사실을 저장한다
            Thread.currentThread().interrupt();
        }
    }

    void processTask(Task task) {
        // Handle the task
    }

    interface Task {
    }
}

