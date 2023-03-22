package com.concurrency.chapter13.예제13_02_ReetrantLock_객체동기화;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock을 사용하는 가장 기본적인 방법이다.
 * finally 블록에서 반드시 락을 해제해야한다!
 * 락을 해제하는 기능을 finally 구문에 넣어두지 않은 코드는 언제 터질지 모르는 시한폭탄과 같다.
 * ReentrantLock을 사용하면 해당하는 블록의 실행이 끝나고 통제권이 해당 블록을 떠나는 순간,
 * 락을 자동으로 해제하지 않기 때문에 굉장히 위험한 코드가 될 가능성이 높다.
 * 락을 블록이 끝나는 시점에 finally 블록을 사용해 해제해야 한다는 사실은 절대 잊으면 안된다.
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        lock.lock();

        try {
            // 객체 내부 값을 사용
            // 예외가 발생한 경우, 적절하게 내부 값을 복원해야할 수도 있음
        } finally {
            lock.unlock();
        }
    }
}
