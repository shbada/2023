package com.concurrency.chapter02.예제2_07_암묵적인락_재진입;

/**
 * 암묵적인 락 (=자바에 내장된 락) ex) synchronized 키워드
 * 재진입이 가능하여 특성 스레드가 자기가 이미 획득한 락을 다시 확보할 수 있다.
 * 재진입성은 확보 요청 단위가 아닌 스레드 단위로 락을 얻는다는 것을 의미한다.
 * 재진입성을 구현하려면 각 락마다 확보 횟수와 확보한 스레드를 연결시켜둔다.
 * 같은 스레드가 락을 다시 얻으면 횟수를 증가시키고, 소유한 스레드가 synchronized 블록을 나가면 감소시킨다.
 * 이렇게 횟수가 0 이되면 해당 락은 해제된다.
 *
 * 락의 재진입이 가능하지 않았다면 데드락에 빠졌을 코드다.
 */
public class _02_LoggingWidget extends _01_Widget {
    /**
     * synchronized 선언
     * Widget, LoggingWidget 둘다 각각 진행전에 락을 얻으려고 시도한다.
     *
     * 암묵적인 락이 재진입 가능하지 않았다면, 이미 락을 누군가가 확보했기 때문에
     * super.doSomething()호출에서 락을 얻을 수 없게되고, 결과적으로 확보할 수 없는 락을 기다리면서 영원히 멈춰있었을 것이다.
     *
     * 1) 스레드 A, B 가 있다.
     * 2) 스레드 A가 LoggingWidget 의 doSomething 메서드에 접근하여 락을 확보한다.
     * 3) B 스레드가 LoggingWidget 의 락을 확보한다.
     * 4) 스레드 A가 다시 기존 메서드로 돌아가기 위해 LoggingWidget 의 락을 확보하기 위해 대기한다.
     * -> 그렇기에 병렬 프로그래밍에선 재진입성을 확보해야하고, 재진입이 가능하다는 전제하에 프로그래밍을 해야한다.
     */
    public synchronized void doSomething() {
        System.out.println(toString() + ": calling doSomething");
        // 해당 메서드도 synchronized 로 선언되어있다.
        /*
        재진입성이 가능하지 않았다면, 이미 락을 누군가가 확보했기 때문에 아래 호출에서 락을 얻을 수 없다.
        결과적으로 확보할 수 없는 락을 기다리게된다.
        재진입성이 가능하지 않았다면 데드락이 발생했을 것이다.
         */
        super.doSomething();
    }
}
