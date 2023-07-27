package me.whiteship.chapter04.item17.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 병행 : 여러 작업을 번갈아 실행해 마치 동시에 여러작업을 동시에 처리하듯 보이지만, 실제로는 한번에 오직 한 작업만 실행한다. (CPU 1개여도 가능)
 * 병렬 : 여러 작업을 동시에 처리한다. (CPU가 여러개여야 가능)
 *
 * CountDownLatch
 * - 초기화할때 숫자를 입력하고, await() 메서드를 사용해서 숫자가 0이 될때까지 기다린다.
 * - 숫자를 셀때는 countDown() 메서드를 사용한다.
 * - 재사용할 수 있는 인스턴스가 아니다. 숫자를 리셋해서 재사용하려면 CyclicBarrier를 사용해야한다.
 * - 시작/종료 신호를 사용할 수 있다.
 */
public class ConcurrentExample2 {
    public static void main(String[] args) throws InterruptedException {
        int N = 10;

        for (int i = 0; i < N; ++i) // create and start threads
            new Thread(new Worker()).start();

        ready();            // don't let run yet
        done();
    }

    private static void ready() {
        System.out.println("준비~~~");
    }

    private static void done() {
        System.out.println("끝!");
    }

    private static class Worker implements Runnable {

        public Worker() {
        }

        public void run() {
            doWork();
        }

        void doWork() {
            System.out.println("working thread: " + Thread.currentThread().getName());
        }
    }
}
