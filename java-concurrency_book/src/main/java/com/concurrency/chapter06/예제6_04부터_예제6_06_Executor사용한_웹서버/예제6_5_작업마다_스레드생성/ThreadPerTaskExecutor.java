package com.concurrency.chapter06.예제6_04부터_예제6_06_Executor사용한_웹서버.예제6_5_작업마다_스레드생성;

import java.util.concurrent.*;

/**
 * ThreadPerTaskExecutor
 * <p/>
 * Executor that starts a new thread for each task
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ThreadPerTaskExecutor implements Executor {
    /**
     * 요청마다 새로운 스레드를 생성해 실행한다.
     * @param r the runnable task
     */
    public void execute(Runnable r) {
        /*
          프로그램 어디든간에, 아래와 같은 코드가 있다면..
          조만간 이런 부분에 유연한 실행 정책을 적용할 준비를 해야할 것이다.
          나중을 위해서 Executor를 사용해 구현하는 방안을 고려해봐야한다.
         */
        new Thread(r).start();
    };
}
