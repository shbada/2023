package com.concurrency.chapter04.예제4_13_기존의Vector_상속받고_메서드추가;

import java.util.*;

import net.jcip.annotations.*;

/**
 * BetterVector
 * <p/>
 * Extending Vector to have a put-if-absent method
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 기존의 Vector 클래스를 상속받아 putIfAbsent 메서드를 추가
 *
 * 필요한 기능을 구현해 추가하면서 스레드 안정성을 계속 유지하는 방법을 찾아야한다.
 *
 * List
 * X 객체를 갖고있지 않은 List 인스턴스에 없을때만 추가하는 연산을 두번 호출한다해도,
 * List 인스턴스에는 X 객체가 1개만 존재해야한다.
 * -> 목록에 들어있지 않은 경우에만 추가하는 연산이 단일 연산이여야하는 조건
 * -> 없을때만 추가하는 연산이 단일 연산이 아니라면 메서드를 호출하는 타이밍이 절묘하게 맞아 떨어져
 *    호출할때 넘겨줬던 동일한 X 객체가 List에 두번 추가될 가능성이 있다.
 *
 * 단일 연산 하나를 기존 클래스에 추가하고자 한다면 해당하는 단일 연산 메서드를 기존 클래스에 직접
 * 추가하는 방법이 안전하다.
 * 외부 라이브러리를 가져다 사용하는 경우에는 라이브러리의 소스코드를 갖고있지 않을 수도 있고,
 * 소스코드를 갖고있다해도 자유롭게 고쳐쓰지 못할 경우가 많다.
 * 기능을 추가하는 또다른 방법은 기존 클래스를 상속받는 방법 - 기존 클래스를 외부에서 상속받아 사용할 수 있도록 설계했을때 사용가능
 *
 */
@ThreadSafe
public class BetterVector <E> extends Vector<E> { /** Vector 상속 */
    // When extending a serializable class, you should redefine serialVersionUID
    static final long serialVersionUID = -3963416950630760754L;

    /**
     * 메서드 추가
     * 기존 클래스(Vector)에 직접 추가하는 방법보다 문제가 생길 위험이 훨씬 많다.
     * 동기화를 맞춰야할 대상이 두개 이상의 클래스에 걸쳐 분산된다.
     * 만약 상위 클래스가 내부적으로 상태 변수의 스레드 안정성을 보장하는 동기화 기법을 조금이라도 수정한다면,
     * 그 하위 클래스는 본의 아니게 적절한 락을 필요한 부분에 적용하지 못할 가능성이 높기 때문에
     * 쥐도새도모르게 동기화가 깨질 수 있다.
     * @param x
     * @return
     */
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
        if (absent)
            add(x);
        return absent;
    }
}
