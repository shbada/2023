package com.concurrency.chapter04.예제4_15_클라이언트측_락_사용하여_추가기능;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * X라는 객체를 사용할때 X 객체가 사용하는 것과 동일한 락을 사용하여 스레드 안정성을 확보해야한다.
 * 클라이언트 측 락 방법을 사용하려면 X 객체가 사용하는 락이 어떤 것인지를 알아야한다.
 *
 * Vector 클래스 자체나 syncronizedList의 결과 List를 통해 클라이언트 측 락을 지원한다.
 * @param <E>
 */
@ThreadSafe
public class GoodListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        /* 클라이언트 측의 락으로 동기화 */
        /*
            클라이언트 측 락을 구현할때, 캡슐화되어있는 동기화 정책을 무너뜨릴 가능성이 있다.
            제3의 클래스를 만들어 클라이언트 측 락 방법으로 단일 연산을 구현하는 방법은,
            특정 클래스 내부에서 사용하는 락을 전혀 관계없는 제3의 클래스에서 갖다쓰기 때문에 훨씬 위험해보인다.

            -> 좀더 나은 방법 (예제4_16. 재구성기법)
         */
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}
