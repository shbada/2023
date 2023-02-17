package com.concurrency.chapter07.예제7_02_1초간_소수계산_프로그램;

import com.concurrency.chapter07.예제7_01_volatile변수사용하여_취소상태확인.PrimeGenerator;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 소수 계산 작업 스레드를 실행시킨 다음 1초후에 소수 계산 작업을 멈추도록 하는 예제다.
 * 소수를 계산하는 작업이 정확하게 1초 후에는 멈춰 있으리라는 보장은 없다.
 * 취소를 요청하는 시점 이후에 run 메서드 내부의 반복문이 cancelled 플래그를 확인할때까지 최소한의 시간이 흘러야 하기 때문이다.
 * cancel 메서드는 finally 구문에서 호출하도록 되어있기 때문에 sleep 메서드를 호출해 대기하던 도중에,
 * 인터럽트가 걸린다해도 소수 계산 작업은 반드시 멈출 수 있다.
 */

/**
 * PrimeGenerator2
 * <p/>
 * Using a volatile field to hold cancellation state
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class PrimeGenerator2 implements Runnable {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    @GuardedBy("this") private final List<BigInteger> primes
            = new ArrayList<BigInteger>();
    private volatile boolean cancelled;

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        exec.execute(generator);

        try {
            SECONDS.sleep(1);
        } finally {
            /**
             * sleep 메서드를 호출해 대기하던 도중에 인터럽트가 걸린다해도 소수 계산 작업은 반드시 멈출 수 있다.
             */
            generator.cancel();
        }
        return generator.get();
    }
}