package com.concurrency.chapter05.예제5_11_CountDownLatch_전체실행시간_측정;

import java.util.concurrent.*;

/**
 - 동기화 클래스
 > 상태 정보를 사용해 스레드 간의 작업 흐름을 조절할 수 있도록 만들어진 모든 클래스
 (세마포어 , 배리어, 래치)

 모든 동기화 클래스는 구조적인 특징을 갖고있다.
 모두 동기화 클래스에 접근하려는 스레드가 어느 경우에 통과하고, 어느 경우에는 대기하도록 멈추게해야 하는지를 결정하는 상태 정보를 갖고있고,
 그 상태를 변경할 수 있는 메서드를 제공하고, 동기화 클래스가 특정 상태에 진입할 때까지 효과적으로 대기할 수 있는 메서드도 제공한다.

 [래치]
 래치는 특정한 단일 동작이 완료되기 이전에는 어떤 기능도 동작하지 않도록 막아내야 하는 경우 사용할 수 있다.
 1) 특정 자원을 확보하기 전에는 작업을 시작하지 말아야 하는 경우
 2) 의존성을 갖고있는 다른 서비스가 시작하기 전에는 특정 서비스가 실행되지 않도록 막아야하는 경우
 -> 각 서비스마다 이진 래치를 갖고ㅓ있으며, S라는 서비스를 시작하면 먼저 S가 의존성을 갖고있는 모든 서비스의 래치가 열리기를 기다린다.
 기다리던 모든 래치가 열리고나면 서비스 S는 자신의 래치를 열어 자신이 시작되기를 기다리는 서비스가 실행될 수 있도록 한다.
 3) 특정 작업에 필요한 모든 객체가 실행할 준비를 갖출때까지 기다리는 경우
 -> 예를들어 여러 사용자가 동시에 참여하는 게임을 최초에 시작하기 전에, 모든 사용자가 게임을 시작할 준비가 끝났는지 확인할때 요긴하다.
 모든 사용자가 준비됐다는 상태에ㅐ 이르면 래치가 터미널 상태에 다다르게 구성할 수 있다.

 CountDownLatch는 하나 또는 둘 이상의 스레드가 여러개의 이벤트가 일어날때까지 대기할 수 있도록 되어있다.
 래치의 상태는 양의 정수 값으로 카운터를 초기화하며, 이 값은 개디하는 동안 발생해야하는 이벤트의 건수를 의미한다.
 CountDownLatch 클래스의 countDown 메서드는 대기하던 이벤트가 발생했을때 내부에 갖고있는 이벤트 카운터를 하나 낮춰주고,
 awit 메서드는 래치 내부의 카운터가 0이 될때까지, 즉 대기하던 이벤트가 모두 발생했을때까지 대기하도록 하는 메서드다.

 외부 스레드가 await 메서드를 호출할때 래치 내부의 카운터가 0보다 큰 값이었다면,
 await 메서드는 카운터가 0이 되거나, 대기하던 스레드에 인터럽트가 걸리거나, 대기시간이 길어 타임아웃이 걸릴때까지 대기한다.

 */

/**
 * TestHarness
 * <p/>
 * Using CountDownLatch for starting and stopping threads in timing tests
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {
        /* 시작하는 관문 */
        final CountDownLatch startGate = new CountDownLatch(1); // 초기화 : 1
        /* 종료하는 관문 */
        final CountDownLatch endGate = new CountDownLatch(nThreads); // 초기화 : 전체 스레드 개수

        for (int i = 0; i < nThreads; i++) {
            /*
            여러개의 스레드를 만들어, 각 스레드가 동시에 실행되도록 한다.
             */
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await(); // 시작관문이 열리기를 기다린다.
                        try {
                            // 시작관문이 열려야 수행되므로, n개의 스레드가 동시에 동작할때 수행된다.
                            task.run();
                        } finally {
                            // 종료관문의 카운트가 계속해서 감소되다보면, 모든 작업 스레드가 끝나는 시점이 온다.
                            // 이 시점이 오면 메인 스레드는 모든 작업 스레드가 작업을 마쳤다는 것을 쉽게 알 수 있다.
                            endGate.countDown(); // 종료관문
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown(); // 시작관문 카운트 감소

        // 종료관문 열리기를 기다린다.
        endGate.await(); // 열리면, 모든 작업 스레드 수행이 끝났다는 의미다.
        long end = System.nanoTime();

        return end - start;
    }
}

