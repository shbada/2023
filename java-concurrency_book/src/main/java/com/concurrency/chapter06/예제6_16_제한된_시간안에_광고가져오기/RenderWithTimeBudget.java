package com.concurrency.chapter06.예제6_16_제한된_시간안에_광고가져오기;

import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * [작업 실행 시간 제한]
 * 일정 시간동안만 기다리고, 시간이 지나버리면 결과를 받지 않을 수 있다.
 * Future.get()은 시간을 지정할 수 있다.
 * 즉 결과가 나오는 즉시 리턴되는 것은 타임아웃을 지정하지 않는 경우와 같지만,
 * 지정한 시간이 지나도 결과를 만들어내지 못하면 TimeoutException을 던지면서 실행이 멈추게 되어있다.
 *
 * 시간 제한을 걸어둔 get 메서드에서 TimeoutException이 발생하면, 해당 Future의 작업을 취소시킬 수 이 ㅆ다.
 * 취소하는 즉시 더이상 시스템 자원을 잡아먹지 않고 깔끔하게 멈출 수 있또록 취소 구현도 해야한다.
 */

/**
 * RenderWithTimeBudget
 *
 * Fetching an advertisement with a time budget
 *
 * @author Brian Goetz and Tim Peierls
 */
public class RenderWithTimeBudget {
    private static final Ad DEFAULT_AD = new Ad();
    private static final long TIME_BUDGET = 1000;
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        Future<Ad> f = exec.submit(new FetchAdTask());
        // Render the page while waiting for the ad
        Page page = renderPageBody();
        Ad ad;

        /**
         * 지정된 시간까지만 기다린다.
         */
        try {
            // Only wait for the remaining time budget
            long timeLeft = endNanos - System.nanoTime();
            ad = f.get(timeLeft, NANOSECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            // default 광고 셋팅
            ad = DEFAULT_AD;
            // 작업 취소
            f.cancel(true);
        }
        page.setAd(ad);
        return page;
    }

    Page renderPageBody() { return new Page(); }


    static class Ad {
    }

    static class Page {
        public void setAd(Ad ad) { }
    }

    static class FetchAdTask implements Callable<Ad> {
        public Ad call() {
            return new Ad();
        }
    }

}
