package com.concurrency.chapter02.예제2_07_암묵적인락_재진입;

/**
 * synchronized
 * 락으로 사용될 객체의 참조 값과 해당 락으로 보호하려는 코드 블록으로 구성된다.
 * 락은 스레드가 synchronized 블록에 들어가기 전에 자동으로 확보되며,
 * 정상적으로든 예외가 발생해서든 해당 블록을 벗어날때 자동으로 해제된다.
 * 해당 락으로 보호된 synchronized 블록이나 메서드에 들어가야만 암묵적인 락을 확보할 수 있다.
 *
 * 자바에서 암묵적인 락은 뮤텍스(mutexes)로 동작한다.
 * 한번에 한 스레드만 특정 락을 소유할 수 있다.
 * 스레드 B가 가지고 있는 락을 스레드 A가 얻으려면, A는 B가 해당 락을 놓을때까지 기다려야한다.
 * 만약 B가 락을 놓지 않으면 A는 영원히 기다려야한다.
 *
 * 특정 락으로 보호된 코드 블록은 한번에 한 스레드만 실행할 수 있기때문에
 * 같은 락으로 보호되는 synchronized 서로 다른 블록 역시 서로 단일 연산으로 실행된다.
 * 한 스레드가 synchronized 블록을 실행중이면 같은 락으로 보호되는 synchronized 블록에 다른 스레드가 들어와있을 수 없다.
 *
 * 응답성은 엄청 떨어질 수도 있다.
 *
 * 스레드가 다른 스레드가 가진 락을 요청하면 해당 스레드는 대기 상태에 들어간다.
 */
public class _01_Widget {
    public synchronized void doSomething() {
        System.out.println("_01_Widget.doSomething");
    }
}