package com.concurrency.chapter04.예제4_14_목록에없으면_추가기능잘못구현;

import java.util.*;

import net.jcip.annotations.*;

/**
 * ListHelder
 * <p/>
 * Examples of thread-safe and non-thread-safe implementations of
 * put-if-absent helper methods for List
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 목록에 없으면 추가하는 기능을 잘못 구현한 예. 이런 코드는 금물!
 *
 * Collections.synchrnozedList 메서드를 사용하여 동기화시킨 ArrayList 에는
 * 1) 기존 클래스에 메서드 추가
 * 2) 상속받은 하위 클래스에서 추가 기능을 구현하는 방법
 * 위 2 가지 방법을 적용할 수 없다.
 * 동기화된 ArrayList를 받아간 외부 프로그램은 받아간 List 객체가 synchronizedList 메서드로 동기화되었는지를
 * 알수가 없기 때문이다.
 *
 * 클래스를 상속받지 않고도 클래스에 원하는 기능을 추가하는 세번째 방법
 * - 도우미 클래스를 따로 구현해서 추가 기능을 구현하는 방법이다.
 */

@NotThreadSafe
class BadListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    /**
     * 스레드 안정성을 확보하지 못한 코드!
     * List가 자체적으로 동기화를 맞추기 위해 어떤 락을 사용했던간에, BadListHelper 클래스와 관련된 락이 아니다.
     * 이 메서드는 List 클래스의 다른 메서드와는 다른 차원에서 동기화되고있다. -> List 입장에서 보면 단일 연산으로 볼수 없다.
     * @param x
     * @return
     */
    public synchronized boolean putIfAbsent(E x) {
        // 동기화되었다고 착각할 수 있음
        // 결과적으로 원래 List의 다른 메서드를 얼마든지 호출해서 내용을 변경할 수 있다.
        // 올바르게 구현하려면, 클라이언트 측 락과 외부 락을 사용해 List가 사용하는 것과 동일한 락을 사용해야한다.
        /*
            X라는 객체를 사용할때 X 객체가 사용하는 것돠 동일한 락을 사용하여 스레드 안정성을 확보해야한다.
            클라이언트 측 락 방법을 사용하려면 X 객체가 사용하는 락이 어떤 것인지를 알아야한다.

            Vector 클래스 자체나 syncronizedList의 결과 List를 통해 클라이언트 측 락을 지원한다.
         */
        boolean absent = !list.contains(x);

        if (absent)
            list.add(x);

        return absent;
    }
}
