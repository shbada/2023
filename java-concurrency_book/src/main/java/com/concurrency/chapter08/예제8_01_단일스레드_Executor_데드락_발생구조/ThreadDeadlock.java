package com.concurrency.chapter08.예제8_01_단일스레드_Executor_데드락_발생구조;

import java.util.concurrent.*;

/**
 * 스레드풀에서 다른 작업에 의존성을 갖고 있는 작업을 실행시킨다면 데드락에 걸릴 가능성이 높다.
 * 단일 스레드로 동작하는 Executor에서 다른 작업을 큐에 등록하고,
 * 해당 작업이 실행된 결과를 가져다 사용하는 작업을 실행하면, 데드락이 제대로 걸린다.
 * 1) 이전 작업은 추가된 작업이 실행되어 그 결과를 알려주기를 기다린다.
 * 2) 스레드풀의 크기가 크더라도 실행되는 모든 스레드가 큐에 쌓여 아직 실행되지 않은 작업의 결과를 받으려고 대기한다.
 * -> 이런 현상을 바로 스레드 부족 데드락(thread starvation deadlock)이라고 한다.
 */

/**
 * ThreadDeadlock
 * <p/>
 * Task that deadlocks in a single-threaded Executor
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {
            // Here's where we would actually read the file
            return "";
        }
    }

    public class RenderPageTask implements Callable<String> {
        /**
         * 페이지 본문 -> 머리글&꼬리글 -> 머리글&본문&꼬리글(최종페이지 생성)
         * @return
         * @throws Exception
         */
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();

            // Will deadlock -- task waiting for result of subtask
            // 완전히 독립적이지 않은 작업을 Executor에 등록할때는 항상 스레드 부족 데드락이 발새할 수 있다.
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            // Here's where we would actually render the page
            return "";
        }
    }
}
