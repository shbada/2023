package com.concurrency.chapter07.예제7_03_계속_대기중인상태_좋지않은예제;

import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * [예제]
 * 프로듀서 스레드는 소수를 찾아내는 작업을 진행하고, 찾아낸 소수는 블로킹 큐에 집어 넣는다.
 * 그런데 컨슈머가 가져가는 것보다 프로듀서가 소수를 찾아내는 속도가 더 빠르다면, 큐는 곧 가득 찰 것이며,
 * 큐의 put 메서드는 블록될 것이다.
 *
 * 이런 상태에서 부하가 걸린 컨슈머가 큐에 put하려고 대기중인 프로듀서의 작업을 취소시키려 한다면?
 * cancel 메서드를 호출해 cancelled 플래그를 설정할 수 있겠지만,
 * 프로듀서는 put 메서드에서 멈춰있고, put 메서드에서 멈춘 작업을 풀어줘야할 컨슈머가 더이상 작업을 처리하지 못하기 때문에,
 * cancelled 변수를 확인할 수 없다.
 */

/**
 * BrokenPrimeProducer
 * <p/>
 * Unreliable cancellation that can leave producers stuck in a blocking operation
 *
 * @author Brian Goetz and Tim Peierls
 */
class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled)
            /**
             *  부하가 걸린 컨슈머가 큐에 put하려고 대기중인 프로듀서의 작업을 취소시키려 한다면?
             *  프로듀서는 put 메서드에서 멈춰있고, put 메서드에서 멈춘 작업을 풀어줘야할 컨슈머가 더이상 작업을 처리하지 못한다.
             */
                queue.put(p = p.nextProbablePrime());
        } catch (InterruptedException consumed) {
        }
    }

    public void cancel() {
        cancelled = true;
    }
}
