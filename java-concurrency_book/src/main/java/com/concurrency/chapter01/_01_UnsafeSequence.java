package com.concurrency.chapter01;

import net.jcip.annotations.NotThreadSafe;

/**
 * 스레드가 1개일 때는 아무런 문제가 없지만, 스레드가 여럿일 때는 제대로 동작하지 않는 예제다.
 * 스레드는 서로 같은 메모리 주소 공간을 공유하고 동시에 실행되기 때문에 다른 스레드가 사용중일지도 모르는
 * 변수를 읽거나 수정할 수도 있다.
 * 이 점은 위험 요소이기도 하다.
 */
@NotThreadSafe
public class _01_UnsafeSequence {
    private int value;

    /**
     * 유일한 값을 리턴
     *
     * 타이밍이 좋지 않은 시점에 두개의 스레드가 getNext 메서드를 동시에 호출한다면?
     * 같은 값을 얻을 가능성이 있다.
     * @return
     */
    public int getNext() {
        return value++;
    }
}
