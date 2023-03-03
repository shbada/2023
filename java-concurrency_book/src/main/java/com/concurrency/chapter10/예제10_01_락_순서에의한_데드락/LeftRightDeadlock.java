package com.concurrency.chapter10.예제10_01_락_순서에의한_데드락;

/**
 * 두 개의 스레드가 서로 다른 순서로 동일한 락을 확보하려고 하기 때문에 데드락이 발생하는 예제
 *
 * [문제가 되는 실행 순서]
 * A -> left 락 확보 -> right 락을 확보하려고 대기 -> 계속해서 대기
 * B -> right 락 확보 -> left 락을 확보하려고 대기 -> 계속해서 대기
 *
 * 프로그램 내부의 모든 스레드에서 필요한 락을 모두 같은 순서로만 사용한다면,
 * 락 순서에 의한 데드락은 발생하지 않는다.
 * 락을 사용하는 순서가 일정한지를 확인하려면 프로그램 내부에서 락을 사용하는 패턴과 방법을 검증해야한다.
 */

/**
 * LeftRightDeadlock
 *
 * Simple lock-ordering deadlock
 *
 * @author Brian Goetz and Tim Peierls
 */
public class LeftRightDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                doSomethingElse();
            }
        }
    }

    void doSomething() {
    }

    void doSomethingElse() {
    }
}
