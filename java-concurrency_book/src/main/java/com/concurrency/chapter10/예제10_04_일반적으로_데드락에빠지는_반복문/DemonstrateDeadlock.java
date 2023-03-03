package com.concurrency.chapter10.예제10_04_일반적으로_데드락에빠지는_반복문;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.concurrency.chapter10.예제10_02_동적인_락_순서에의한_데드락.DynamicOrderDeadlock.transferMoney;

/**
 * 락은 짧은 시간 동안만 사용할수록 락 때문에 문제가 생기는 경우가 줄어들겠지만,
 * 반대로 테스트 과정에서 데드락이 걸리는 문제를 찾아내기가 더 힘들어진다.
 *
 * 아래 예제는 대부분의 시스템에서 아주 금방 데드락 상태에 빠지는 예를 보여준다.
 */

/**
 * DemonstrateDeadlock
 * <p/>
 * Driver loop that induces deadlock under typical conditions
 *
 * @author Brian Goetz and Tim Peierls
 */
public class DemonstrateDeadlock {
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 1000000;

//    public static void main(String[] args) {
//        final Random rnd = new Random();
//        final Account[] accounts = new Account[NUM_ACCOUNTS];
//
//        for (int i = 0; i < accounts.length; i++)
//            accounts[i] = new Account();
//
//        class TransferThread extends Thread {
//            public void run() {
//                for (int i = 0; i < NUM_ITERATIONS; i++) {
//                    int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
//                    int toAcct = rnd.nextInt(NUM_ACCOUNTS);
//                    DollarAmount amount = new DollarAmount(rnd.nextInt(1000));
//                    try {
//                        transferMoney(accounts[fromAcct], accounts[toAcct], amount);
//                    } catch (InsufficientFundsException ignored) {
//                    }
//                }
//            }
//        }
//        for (int i = 0; i < NUM_THREADS; i++)
//            new TransferThread().start();
//    }
}
