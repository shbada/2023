package com.concurrency.chapter05.예제5_15_CycleBarrier_사용하여_셀룰러_오토마타의_연산제어;

import java.util.concurrent.*;

/**
 배리어는 특정 이벤트가 발생할 때까지 여러개의 스레드를 대기 상태로 잡아둘 수 있다.
 래치와의 차이점은 모든 스레드가 배리어 위치에 동시에 이르러야 관문이 열리고 계속 실행할 수 있다는 점이 다르다.
 래치는 '이벤트'를 기다리는 동기화 클래스이고, 배리어는 '다른 스레드'를 기다리는 동기화 클래스다.

 [CyclicBarrier]
 여러 스레드가 특정한 배리어 포인트에서 반복적으로 서로 만나는 기능을 모델링할 수 있다.
 커다란 문제 하나를 여러개의 작은 부분 문제로 분리해 반복적으로 병렬 처리하는 알고리즘을 구현하고자할 때 적용하기 좋다.

 스레드는 각자의 배리어 포인트에 다다르면 await 메서드를 호출하며,
 await 메서드는 모든 스레드가 배리어 포인트에 도달할 때까지 대기한다.
 모든 스레드가 배리어 포인트에 도달하면 배리어는 모든 스레드를 통과시키며, await 메서드에서 대기하고 있던 스레드는 대기 상태가 모두 풀려 실행되고,
 배리어는 다시 초기 상태로 돌아가 다음 배리어 포인트를 준비한다.
 만약 await를 호출하고 시간이 너무 오래걸려서 타임아웃이 걸리거나, await 메섣드에서 대기하던 스레드에 인터럽트가 걸리면 배리어는 깨진것으로 간주하고,
 await에서 대기하던 모든 스레드에 BrokenBarrierException이 발생한다.
 배리어가 성공적으로 통과하면 await 메서드는 각 스레드별로 배리어 포인트에 도착한 순서를 알려주며,
 다음 배리어 포인트로 반복 작업을 하는 동안 뭔가 특별한 작업을 진행할 일종의 리더를 선출하는데 이 값을 사용할 수 있다.
 CyclerBariier 클래스는 생성 메서드를 통해 배리어 작업을 넘겨받을 수 있도록 되어있다.
 배리어 작업은 Runnable 인터페이스를 구현한 클래스인데, 배리어 작업은 배리어가 성공적으로 통과된 이후 대기하던 스레드를 놓아주기 직전에 실행된다.

 배리어는 대부분 실제 작업은 모두 여러 스레드에서 병렬로 처리하고, 다음 단계로 넘어가기 전에 이번 단계에서 계산해야할 내용을 모두 취합해야하는 등의 작업이 많아 일어나는 시뮬레이션 알고리즘에서 유용하게 사용할 수 있다.

 [예제]
 셀 단위로 처리하는 대신 전체 면적을 특정한 크기의 부분으로 나누고,
 각 스레드가 전체 면적의 일부분을 처리하고, 처리가 끝난 결과를 다시 하나로 뭉쳐 전체 결과를 재구성할 수 있겠다.
 CellularAutomata 클래스는 전체면적을 Ncpu개의 부분으로 나누고, 각 부분에 대한 연산을 개별 스레드에게 맡긴다.
 각 단계에서 작업하는 스레드 모두가 계산 작업을 마치고 나면 배리어 포인트에 도달하고,
 배리어 작업이 그동안 모인 부분 결과를 하나로 묶어 전체 결과를 만들어낸다.
 배리어 작업이 모두 끝나고 나면 작업 스레드는 모두 대기 상태가 풀려 다음 단계의 연산을 시작한다.
 더이상 작업할 단계가 없는 시점에 이르렀는지 확인할때에는 isDone이라는 메서드를 사용한다.
 */

/**
 * CellularAutomata
 *
 * Coordinating computation in a cellular automaton with CyclicBarrier
 *
 * @author Brian Goetz and Tim Peierls
 */
public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count,
                new Runnable() {
                    public void run() {
                        mainBoard.commitNewValues();
                    }});
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++)
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) { this.board = board; }
        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++)
                    for (int y = 0; y < board.getMaxY(); y++)
                        board.setNewValue(x, y, computeValue(x, y));
                try {
                    barrier.await();
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private int computeValue(int x, int y) {
            // Compute the new value that goes in (x,y)
            return 0;
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++)
            new Thread(workers[i]).start();
        mainBoard.waitForConvergence();
    }

    interface Board {
        int getMaxX();
        int getMaxY();
        int getValue(int x, int y);
        int setNewValue(int x, int y, int value);
        void commitNewValues();
        boolean hasConverged();
        void waitForConvergence();
        Board getSubBoard(int numPartitions, int index);
    }
}

