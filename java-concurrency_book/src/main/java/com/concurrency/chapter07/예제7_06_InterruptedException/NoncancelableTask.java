package com.concurrency.chapter07.예제7_06_InterruptedException;

import java.util.concurrent.*;

/**
 * ThreadPoolExecutor 내부의 풀에 등록되어있는 스레드에 인터럽트가 걸렸다면,
 * 인터럽트가 걸린 스레드는 전체 스레드 풀이 종료되는 상태인지를 먼저 확인한다.
 * 스레드 풀 자체가 종료되는 상태였다면 스레드를 종료하기 전에 스레드 풀을 정리하는 작업을 실행하고,
 * 스레드 풀이 종료되는 상태가 아니라면 스레드 풀에서 동작하는 스레드의 수를 그대로 유지시킬 수 있도록
 * 새로운 스레드를 하나 생성해 풀에 등록시킨다.
 */

/**
 * NoncancelableTask
 * <p/>
 * Noncancelable task that restores interruption before exit
 *
 * @author Brian Goetz and Tim Peierls
 */
public class NoncancelableTask {
    public Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    // 인터럽트 상태를 내부적으로 보관하고 있다가,
                    interrupted = true;
                    // fall through and retry
                }
            }
        } finally {
            // 메서드 리턴 전에 인터럽트 상태를 원래대로 복구하고 리턴되도록 해야한다.
            if (interrupted)
            /**
             * 대부분의 프로그램 코드는 자신이 어느 스레드에서 동작할지 모르기 때문에,
             * 인터럽트 상태를 최대한 그대로 유지해야한다.
             */
                Thread.currentThread().interrupt();
        }
    }

    interface Task {
    }
}
