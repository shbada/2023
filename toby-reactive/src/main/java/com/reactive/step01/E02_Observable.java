package com.reactive.step01;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class E02_Observable {
    // Observable 의 한계
    // 1. Complete를 어떻게 시킬것인가?
    // 2. Error를 어떻게 처리할것인가?
    // 위 2가지를 확장해서 다시 만든것이 Reactive 프로그래밍의 한 축이다.

    public static void main(String[] args) {
        // Iterable <--상대성(duality)--> Observable
        // Iterable [Pull] : 가져오는 것 (next() 메서드로 원하는 결과값을 리턴으로 가져온다.)
        // Observable [Push] : 데이터를 주는것
        // Observable : eventSource -> event를 던진다. -> Target(Observer ;관찰자)

        Observer ob = new Observer() { // Subscriber
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() + ":" + arg);
            }
        };

        IntObservable intObservable = new IntObservable();
        intObservable.addObserver(ob); // Observer 추가

        // main 스레드가 아닌 별도의 스레드로 동작하도록
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(intObservable);
//        intObservable.run();

        // main 쓰레드
        System.out.println(Thread.currentThread().getName() + " EXIT");

        es.shutdown();
    }

    // DATA method(void) <-> void method(DATA)
    // 위 2개가 동일한 기능

    static class IntObservable extends Observable implements Runnable { // Publisher
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i); // 값을 던진다.
            }
        }
    }
}
