package com.concurrency.chapter03.예제3_08_this변수_유출을막는다_팩토리메소드;


/**
 * SafeListener
 * <p/>
 * Using a factory method to prevent the this reference from escaping during construction
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 생성 메서드를 실행하는 도중에는 this 변수가 외부에 유출되지 않게 해야한다.
 */
public class SafeListener {
    private final EventListener listener;

    /**
     * private 지정
     */
    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    /**
     * 팩토리 메서드
     * public으로 지정된 팩토리 메서드를 별도로 만들어서 제공한다.
     * @param source
     * @return
     */
    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener(); // 객체 생성
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}