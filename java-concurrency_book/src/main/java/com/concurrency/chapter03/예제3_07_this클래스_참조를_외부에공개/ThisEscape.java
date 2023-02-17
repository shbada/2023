package com.concurrency.chapter03.예제3_07_this클래스_참조를_외부에공개;


/**
 * ThisEscape
 * <p/>
 * Implicitly allowing the this reference to escape
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 이런 코드는 금물!
 */
public class ThisEscape {
    public ThisEscape(EventSource source) {
        /**
         * 객체를 외부에 공개하면 EventListener 를 포함하고있는 ThisEscape 까지 모두 공개된다.
         * 내부 클래스는 항상 부모 클래스에 대한 참조를 갖고있기 때문이다.
         *
         * 생성자를 실행하는 과정에서 this 변수를 외부에 유출했다.
         */
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
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
