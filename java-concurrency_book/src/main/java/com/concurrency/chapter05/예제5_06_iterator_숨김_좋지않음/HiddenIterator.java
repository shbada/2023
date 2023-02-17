package com.concurrency.chapter05.예제5_06_iterator_숨김_좋지않음;

import java.util.*;

import net.jcip.annotations.*;

/**
 * HiddenIterator
 * <p/>
 * Iteration hidden within string concatenation
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 이런 코드는 금물!
 *
 * 락을 걸어 동기화시키면 Iterator 을 사용할때 ConcurrentModificationException 이 발생하지 않도록 제어할 수는 있다.
 * 하지만 컬렉션을 공유해 사용하는 모든 부분에서 동기화를 맞춰야한다.
 * Iterator이 내부에 숨겨져있는 경우도 있다.
 */
public class HiddenIterator {
    @GuardedBy("this") private final Set<Integer> set = new HashSet<Integer>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++)
            add(r.nextInt());

        /**
         * 문자열 연결 연산 내부에 iteraotr 이 숨겨져있는 상황
         * 컴파일러는 이 문장을 "".append(set) 으로 변환한다.
         * 그 과정에서 컬렉션 클래스의 toString 을 호출하고,
         * 여기서 toString 은 컬렉션 클래스의 iterator 메서드를 호출하여 내용으로 보관하고 있는 개별 클래스의
         * toString 메서드를 호출하여 출력할 문자열을 만든다.
         *
         * HiddenIterator 클래스의 addTenThings 메서드를 실행하는 도중에
         * 디버깅 메시지를 출력하기 위해 set 변수의 Iterator을 찾아 사용하기 때문에,
         * ConcurrentModificationException이 발생할 가능성이 있다.
         *
         * 컬렉션의 toString 메서드 뿐만 아니라 컬렉션 클래스의 hashCode, equals 등도 모두 iterator을 내부적으로 사용한다.
         */
        System.out.println("DEBUG: added ten elements to " + set);
    }
}