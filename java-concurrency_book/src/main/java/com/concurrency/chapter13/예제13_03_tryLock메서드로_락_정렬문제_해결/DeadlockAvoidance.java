package com.concurrency.chapter13.예제13_03_tryLock메서드로_락_정렬문제_해결;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 데드락을 피할 수 있는 방법
 * 1) 시간 제한
 * 일정한 시간을 두고 객체를 관리하는 기능을 구현할때 시간 제한이 있는 락을 적용하면 유용하다.
 * 일정 시간 이내에 실행돼야 하는 코드에서 대기 상태에 들어갈 수 있는 블로킹 메서드를 호출해야한다면,
 * 지정된 시간에서 현재 남아있는 시간만큼을 타임아웃으로 지정할 수 있다.
 * 그러면 지정시간 이내에 결과를 내지 못하는 상황이 되면 알아서 기능을 멈추고 종료되도록 만들 수 있다.
 * 2) 폴링
 */

/**
 * DeadlockAvoidance
 * <p/>
 * Avoiding lock-ordering deadlock using tryLock
 *
 * @author Brian Goetz and Tim Peierls
 */
public class DeadlockAvoidance {
    private static Random rnd = new Random();

    public boolean transferMoney(Account fromAcct,
                                 Account toAcct,
                                 DollarAmount amount,
                                 long timeout,
                                 TimeUnit unit)
            throws InsufficientFundsException, InterruptedException {
        long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
        long randMod = getRandomDelayModulusNanos(timeout, unit);
        long stopTime = System.nanoTime() + unit.toNanos(timeout);

        while (true) {
            /* 양쪽 락을 모두 확보 */
            if (fromAcct.lock.tryLock()) {
                try {
                    if (toAcct.lock.tryLock()) {
                        try {
                            if (fromAcct.getBalance().compareTo(amount) < 0)
                                throw new InsufficientFundsException();
                            else {
                                fromAcct.debit(amount);
                                toAcct.credit(amount);
                                return true;
                            }
                        } finally {
                            toAcct.lock.unlock();
                        }
                    }
                } finally {
                    fromAcct.lock.unlock();
                }
            }
            /* 지정된 시간 이내에 락을 확보하지 못했을때 */
            if (System.nanoTime() < stopTime)
                return false;
            /* 만약 모두 확보할 수 없다면 잠시 대기했다가 재시도 */
            NANOSECONDS.sleep(fixedDelay + rnd.nextLong() % randMod);
        }
    }

    private static final int DELAY_FIXED = 1;
    private static final int DELAY_RANDOM = 2;

    static long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
        return DELAY_FIXED;
    }

    static long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
        return DELAY_RANDOM;
    }

    static class DollarAmount implements Comparable<DollarAmount> {
        public int compareTo(DollarAmount other) {
            return 0;
        }

        DollarAmount(int dollars) {
        }
    }

    class Account {
        public Lock lock;

        void debit(DollarAmount d) {
        }

        void credit(DollarAmount d) {
        }

        DollarAmount getBalance() {
            return null;
        }
    }

    class InsufficientFundsException extends Exception {
    }
}
