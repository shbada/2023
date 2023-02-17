package com.concurrency.chapter07.예제7_05_인터럽트로_작업취소;

import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * 모든 스레드는 불린 값으로 인터럽트 상태를 갖고있다.
 * 스레드에 인터럽트를 걸면 인터럽트 상태 변수의 값이 true로 설정된다.
 * 1) interuupt()
 * - 스레드에 인터럽트를 거는 역할을 한다
 *
 * 2) isInterrupted()
 * - 해당 스레드에 인터럽트가 걸려있는지의 여부
 *
 * 3) static interrupted()
 * - 현재 스레드의 인터럽트 상태를 해제하고, 해제하기 이전의 값이 무엇이었는지를 알려준다.
 * - 해당 메서드는 인터럽트 상태를 해제할 수 있는 유일한 방법이다.
 * - 현재 스레드의 인터럽트 상태를 초기화하기 때문에 사용할때에 상당히 주의를 기울여야한다.
 *   interrupted()를 호출했는데 결과값으로 true가 넘어왔다면, 만약 인터럽트 요청을 무시할 생각이 아니라면 인터럽트에 대응하는 어떤 작업을 진행해야한다.
 *   InterruptedException을 띄우거나 interrupt 메서드를 호출해 인터럽트 상태를 다시 되돌려둬야한다.
 *
 *
 * Thread.sleep, Object wait 등과 같은 블로킹 메서드는 인터럽트 상태를 확인하고 있다가,
 * 인터럽트가 걸리면 즉시 리턴된다.
 * 위 메서드들은 대기하던 중에 인터럽트가 걸리면 인터럽트 상태를 해제하면서 InteruuptedException을 던진다.
 * 여기서 던지는 InterruptedException은 인터럽트가 발생해 대기중이던 상태가 예상보다 빨리 끝났다는 것을 뜻한다.
 * Thread.sleep, Object.wait 등의 메서드에서 인터럽트가 걸렸을때, 인터럽트가 걸렸다는 사실을 얼마나 빠르게 확인하는지는
 * JVM에서도 아무런 보장을 하지 않는다. 일반적으로 무리하게 늦게 반응하는 경우는 없다.
 *
 *
 * [스레드가 블록되어 있지 않은 실행 상태에서 인터럽트가 걸린다면?]
 * - 먼저 인터럽트 상태 변수가 설정되긴 하지만, 인터럽트가 걸렸는지 확인하고, 인터럽트가 결렸을 경우 그에 대응하는 일은
 * 해당 스레드에서 알아서 해야한다.
 * InterruptedException이 발생하거나 하지않기 때문에 해야할 일을 확인하고 처리하는 것은 당사자가 알아서 할 일이다.
 *
 * '특정 스레드의 interrupt 메서드를 호출한다 해도 해당 스레드가 처리하던 작업을 멈추지 않는다.
 * 단지, 해당 스레드에게 인터럽트 요청이 있었다는 메시지를 전달할 뿐이다.'
 *
 * - 인터럽트는 실행중인 스레드에 실제적인 제한을 가해 멈추도록 하지 않는다는 것이 중요하다.
 * 단지 해당하는 스레드가 상황을 봐서 스스로 멈춰주기를 요청하는 것 뿐이다.
 * - sleep, wait, join과 같은 메서드는 인터럽트 요청을 처리할때 실제로 인터럽트 요청을 받거나 실행할때 인터럽트 상태라고 지정했던 시점이 되는 순간 예외를 띄운다.
 */

/**
 * PrimeProducer
 * <p/>
 * Using interruption for cancellation
 *
 * @author Brian Goetz and Tim Peierls
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            /**
             * 인터럽트를 사용
             * 인터럽트 상태를 확인하는 부분
             * 1) 큐의 put 메서드를 호출하는 부분
             * 2) 반복문의 조건 확인 부분에사 인터럽트 상태를 직접 확인
             *
             * 인터럽트가 걸렸을때 InterruptedException을 띄우는 put 메서드만을 사용하므로,
             * 인터럽트 상태를 직접 확인하는 부분을 꼭 둬야할 필요는 없다.
             * 하지만 반복문 앞에서 인터럽트 상태를 확인하면 미리 작업을 시작조차 하지 않을 수 있어서 CPU 등의 자원을 덜 사용한다.
             * 인터럽트에 반응하는 블로킹 메서드를 상대적으로 적게 사용하고 있다면,
             * 반복문의 조건 확인 부분에서 인터럽트 여부를 확인하는 방법으로 응답 속도를 개선할 수 있다.
             */
            while (!Thread.currentThread().isInterrupted())
                queue.put(p = p.nextProbablePrime());
        } catch (InterruptedException consumed) {
            /* Allow thread to exit */
        }
    }

    public void cancel() {
        interrupt();
    }
}

