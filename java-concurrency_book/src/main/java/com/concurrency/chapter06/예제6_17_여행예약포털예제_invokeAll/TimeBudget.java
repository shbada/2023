package com.concurrency.chapter06.예제6_17_여행예약포털예제_invokeAll;

import java.util.*;
import java.util.concurrent.*;

/**
 * 입찰정보를 가져오는 작업 n개를 생성해 스레드 풀에 등록하고, 등록된 작업마다 Future 객체를 확보하고, 타임아웃을 지정한 get 메서드로 각각의 입찰정보를 가져오도록 할 수 있다.
 * -> invokeAll()
 *
 * [invokeAll()]
 * - 여러개의 작업을 ExecutorService에 등록해 실행시키고 결과를 받아오는 부분에 타임아웃을 지정한 invokeAll 메서드를 활용하고 있다.
 * - 작업 객체가 담긴 컬렉션 객체를 넘겨받으며, 그에 해당하는 Future 객체가 담긴 컬렉션 객체를 리턴한다.
 * - invokeAll 메서드는 넘겨받은 작업 컬렉션의 iterator가 뽑아주는 순서에 따라 결과 컬렉션에 Future 객체를 쌓는다.
 * - 시간 제한이 있는 invokeAll 메서드는 등록된 모든 작업이 완료됐거나, 작업을 등록한 스레드에 인터럽트가 걸리거나,
 *   지정된 제한 시간이 지날때까지 대기하다가 리턴된다.
 * - 제한시간이 지날때까지 실행중이던 작업은 모두 실행이 취소된다.
 * - invokeAll 메서드가 리턴되면 등록된 모든 작업은 완료되어 결과값을 가지고 있거나 취소되거나 두가지 상태 가운데 하나이다.
 * - 작업을 등록했던 스레드는 모든 작업을 대상으로 get 메서드를 호출하거나 isCancelled 메서드를 사용해 작업이 완료되거나 취소된 상태를 확인할 수 있다.
 *
 */

/**
 * QuoteTask
 * <p/>
 * Requesting travel quotes under a time budget
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimeBudget {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo, Set<TravelCompany> companies,
                                                   Comparator<TravelQuote> ranking, long time, TimeUnit unit)
            throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<QuoteTask>();
        for (TravelCompany company : companies)
            tasks.add(new QuoteTask(company, travelInfo));

        /**
         * invokeAll()
         */
        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);

        List<TravelQuote> quotes =
                new ArrayList<TravelQuote>(tasks.size());
        Iterator<QuoteTask> taskIter = tasks.iterator();
        for (Future<TravelQuote> f : futures) {
            QuoteTask task = taskIter.next();
            try {
                quotes.add(f.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                quotes.add(task.getTimeoutQuote(e));
            }
        }

        Collections.sort(quotes, ranking);
        return quotes;
    }

}

class QuoteTask implements Callable<TravelQuote> {
    private final TravelCompany company;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    TravelQuote getFailureQuote(Throwable t) {
        return null;
    }

    TravelQuote getTimeoutQuote(CancellationException e) {
        return null;
    }

    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }
}

interface TravelCompany {
    TravelQuote solicitQuote(TravelInfo travelInfo) throws Exception;
}

interface TravelQuote {
}

interface TravelInfo {
}
