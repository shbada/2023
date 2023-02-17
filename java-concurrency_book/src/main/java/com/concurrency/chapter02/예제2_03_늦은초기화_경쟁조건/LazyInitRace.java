package com.concurrency.chapter02.예제2_03_늦은초기화_경쟁조건;

import net.jcip.annotations.*;

/**
 * LazyInitRace
 * 이런 코드는 금물!
 */

@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    /**
     * 특정 객체가 실제 필요할때까지 초기화를 미루고 동시에 단 한번만 초기화되도록 하기 위한 것
     * @return
     */
    public ExpensiveObject getInstance() {
        /* null 조건 - 접근하는 타이밍에 따라 반드시 한번만 호출된다는 보장이 없다.
        따라서 두 스레드가 서로 다른 인스턴스를 가져갈 수 있다. */
        if (instance == null) {
            instance = new ExpensiveObject();
        }

        return instance;
    }
}

class ExpensiveObject { }
