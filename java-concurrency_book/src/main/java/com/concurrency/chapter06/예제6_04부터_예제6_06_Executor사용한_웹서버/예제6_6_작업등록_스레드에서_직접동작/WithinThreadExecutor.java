package com.concurrency.chapter06.예제6_04부터_예제6_06_Executor사용한_웹서버.예제6_6_작업등록_스레드에서_직접동작;

import java.util.concurrent.*;

/**
 * WithinThreadExecutor
 * <p/>
 * Executor that executes tasks synchronously in the calling thread
 *
 * @author Brian Goetz and Tim Peierls
 */
public class WithinThreadExecutor implements Executor {
    /**
     * 작업을 순차적으로 처리한다.
     * execute 메서드 안에서 요청에 대한 처리 작업을 모두 실행하고, 처리가 끝나면 executor에서 리턴되도록 구현한다.
     * @param r the runnable task
     */
    public void execute(Runnable r) {
        r.run();
    };
}
