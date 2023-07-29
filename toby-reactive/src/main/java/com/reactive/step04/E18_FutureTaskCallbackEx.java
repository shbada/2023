package com.reactive.step04;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class E18_FutureTaskCallbackEx {
    /**
     * 성공 콜백
     */
    interface SuccessCallback {
        void onSuccess(String result);
    }

    /**
     * 에러 콜백
     */
    interface ExceptionCallback {
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String> {
        SuccessCallback sc;
        ExceptionCallback ec;

        public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
            super(callable);

            this.sc = Objects.requireNonNull(sc); // null 이 아니여야하고, null 이면 NullPointerException 발생
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done() {
            try {
                sc.onSuccess(get());
            } catch (InterruptedException e) { // 예외 발생시, main 스레드에 어떻게 던져줄까?
                // interrupt 발생 시그널을 주자.
                Thread.currentThread().interrupt();
                // 작업을 수행하지말고 종료하라는 signal을 주는 에러
            } catch (ExecutionException e) {
                ec.onError(e.getCause());
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask f = new CallbackFutureTask(() -> {
            Thread.sleep(2000);

//            if (1 == 1) {
//                throw new RuntimeException("Aysnc ERROR!!!"); // 고의 에러 발생
//            }

            log.info("Async");
            return "Hello";
        }, new SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                System.out.println("Result: " + result);
            }
        }, new ExceptionCallback() {
            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });

        es.execute(f);
        es.shutdown();
    }
}
