package com.concurrency.chapter10.예제10_02_동적인_락_순서에의한_데드락;

import java.util.concurrent.atomic.*;

/**
 * 모든 스레드가 락을 동일한 순서로 확보하려고 할때 데드락이 발생할 수 있는데,
 * 여기에서 락을 확보하는 순서는 전반적으로 transferMoney()를 호출할때 넘겨주는 인자의 순서에 달려있다.
 *
 * A : transferMoney(myAccount, yourAccount, 10);
 * B : transferMoney(yourAccount, myAccount, 20);
 *
 * 타이밍이 딱 맞아떨어진다면, A 스레드는 먼저 myAccount에 대한 락을 확보한 다음, youtAccount 락을 확보하려 할것이고,
 * B 스레드는 yourAccount 락을 확보한 다음 myAccount 락을 확보하려 할 것이다.
 */

/**
 * DynamicOrderDeadlock
 * <p/>
 * Dynamic lock-ordering deadlock
 *
 * @author Brian Goetz and Tim Peierls
 */
public class DynamicOrderDeadlock {
    // Warning: deadlock-prone!
    public static void transferMoney(Account fromAccount,
                                     Account toAccount,
                                     DollarAmount amount)
            throws InsufficientFundsException {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getBalance().compareTo(amount) < 0)
                    throw new InsufficientFundsException();
                else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }

    static class DollarAmount implements Comparable<DollarAmount> {
        // Needs implementation

        public DollarAmount(int amount) {
        }

        public DollarAmount add(DollarAmount d) {
            return null;
        }

        public DollarAmount subtract(DollarAmount d) {
            return null;
        }

        public int compareTo(DollarAmount dollarAmount) {
            return 0;
        }
    }

    static class Account {
        private DollarAmount balance;
        private final int acctNo; // 유일, 불변

        // 내부의 키를 기준으로 정렬한 다음 정렬한 순서대로 락을 확보한다면, 락이 걸리는 순서를 일정하게 유지할 수 있다.
        private static final AtomicInteger sequence = new AtomicInteger();

        public Account() {
            acctNo = sequence.incrementAndGet();
        }

        void debit(DollarAmount d) {
            balance = balance.subtract(d);
        }

        void credit(DollarAmount d) {
            balance = balance.add(d);
        }

        DollarAmount getBalance() {
            return balance;
        }

        int getAcctNo() {
            return acctNo;
        }
    }

    static class InsufficientFundsException extends Exception {
    }
}
