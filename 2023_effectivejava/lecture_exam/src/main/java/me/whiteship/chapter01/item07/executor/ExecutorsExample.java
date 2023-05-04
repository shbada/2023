package me.whiteship.chapter01.item07.executor;

import java.util.concurrent.*;

/**
 * ShceduledThreadPoolExecutor
 *
 * - Thread, Runnable, ExecutorService
 *
 *
 * 스레드 풀 개수 지정시 크게 2가지에 신경써야한다.
 * 1) CPU 작업이 많은가?
 * 아무리 쓰레드 개수를 늘려도, CPU 개수를 넘어가면 어차피 막힘 (CPU 개수만큼만 만들게됨)
 * 2) I/O 작업이 많은가?
 * DB, Network HTTP Call 등을 통한 작업
 */
public class ExecutorsExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // CPU 개수 조회
        int numberOfCpu = Runtime.getRuntime().availableProcessors();

        ExecutorService service = Executors.newFixedThreadPool(10);

        Future<String> submit = service.submit(new Task());

        System.out.println(Thread.currentThread() + " hello");

        System.out.println(submit.get());

        service.shutdown();
    }

    static class Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(2000L);
            return Thread.currentThread() + " world";
        }
    }
}
