package com.concurrency.chapter05.예제5_12_FutureTask_필요한데이터_미리_읽기;

import java.util.concurrent.*;

/**
 FutureTask 는 래치와 비슷한 형태로 동작하낟. (예제5_11)
 FutureTask가 나타내는 연산 작업은 Callable 인터페이스를 구현하도록 되어있는데, 아래와 같이 3가지 상태를 가질 수 있다.
 1) 시작 전 대기
 2) 시작됨
 3) 종료됨 (정상적인 종료, 취소, 예외 상황 발생 등과 같이 연산이 끝나는 모든 종류의 상태)

 FutureTask의 작업이 종료됐다면 get 메서드는 그 결과를 즉시 알려준다.
 종료상태에 이르지 못했을 경우 get 메서드는 작업이 종료 상태에 이를때까지 대기하고, 종료된 이후에 연산 결과나 예외 상황을 알려준다.
 FutureTask는 실제로 연산을 실행했던 스레드에서 만들어낸 결과 객체를 실행시킨 스레드에게 넘겨준다.

 Executor 프레임웍에서 비동기적인 작업을 실행하고자할 때 사용한다.
 기타 시간이 많이 필요한 모든 작업이 있을때 실제 결과가 필요한 시점 이전에 미리 작업을 실행시켜두는 용도로 사용한다.
 */

/**
 * Preloader
 *
 * Using FutureTask to preload data that is needed later
 *
 * @author Brian Goetz and Tim Peierls
 */

public class Preloader {
    ProductInfo loadProductInfo() throws DataLoadException {
        return null;
    }

    private final FutureTask<ProductInfo> future =
            /* 작업 시간이 많이 걸리는걸 미리 시작하여 실제로 결과가 필요로 하는 시점이 됬을때 기다리는 시간을 줄인다 */
            new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
                public ProductInfo call() throws DataLoadException {
                    return loadProductInfo();
                }
            });

    /* futureTask 를 실제로 실행할 스레드를 생성한다. */
    private final Thread thread = new Thread(future);

    public void start() { thread.start(); }

    public ProductInfo get()
            throws DataLoadException, InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) { // get() 에서 ExecutionException 으로 한번 감싼 다음 다시 throw 한다.
            Throwable cause = e.getCause(); // 원인을 받아서,
            if (cause instanceof DataLoadException) // 처리
                throw (DataLoadException) cause;
            else // Callable이 던지는 예외, RuntimeException, Error 3가지를 묶어서 LaunderThrowable 유틸리티 메서드를 사용하자.
                /*
                launderThrowable()
                 */
                throw LaunderThrowable.launderThrowable(cause);
        }
    }

    interface ProductInfo {
    }
}

class DataLoadException extends Exception { }
