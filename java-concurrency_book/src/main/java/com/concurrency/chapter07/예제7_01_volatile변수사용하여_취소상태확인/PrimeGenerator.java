package com.concurrency.chapter07.예제7_01_volatile변수사용하여_취소상태확인;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

import net.jcip.annotations.*;

/**
 * 취소 요청이 들어올 때까지 계속해서 소수를 찾아내는 작업을 진행한다.
 * 취소 요청 플래그를 사용해 작업을 멈추는 방법을 사용한다.
 * cancel 메서드를 호출하면 cancelled 플래그에 true 값이 설정된다.
 * 반복문이 반복될때마다 다음 소수를 계산하기 전에 cancelled 값을 확인하도록 되어있다.
 * -> 안정적으로 동작하게 하기위해서 cancelled 변수를 반드시 volatile 형식으로 선언해야한다.
 */

/**
 * PrimeGenerator2
 * <p/>
 * Using a volatile field to hold cancellation state
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class PrimeGenerator implements Runnable {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    @GuardedBy("this") private final List<BigInteger> primes
            = new ArrayList<BigInteger>();
    
    /**
     * volatile 형식
     */
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
}

