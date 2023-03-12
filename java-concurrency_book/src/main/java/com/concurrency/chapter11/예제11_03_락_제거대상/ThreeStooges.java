package com.concurrency.chapter11.예제11_03_락_제거대상;

import java.util.*;

import net.jcip.annotations.*;

/**
 * 비경쟁적인 동기화 기법
 * 전반적인 애플리케이션 성능의 측면에서 봤을때 비경쟁적이면서 꼭 필요한 동기화 방법은 성능에 그다지 큰 영향이 없다.
 * 최근의 JVM은 대부분의 다른 스레드와 경쟁할 가능성이 없다고 판단되는 부분에 락이 걸려있다면,
 * 최적화 과정에서 해당 락을 사용하지 않도록 방지하는 기능을 제공하기도 한다.
 * 예를들어, 락을 거는 객체가 특정 스레드 내부에 한정돼있다면, 해당 락을 다른 스레드에서 사용하며 경쟁조건에 들어갈 수 없기 때문에
 * JVM은 자동으로 해당 락은 무시하고 넘어간다.
 *
 * 아래 예제는 이와 같은 상황으로, JVM이 락을 사용하지 않는다.
 */

/**
 * ThreeStooges
 * <p/>
 * Immutable class built out of mutable underlying objects,
 * demonstration of candidate for lock elision
 *
 * @author Brian Goetz and Tim Peierls
 */
@Immutable
public final class ThreeStooges {
    private final Set<String> stooges = new HashSet<String>();

    public ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }

    /**
     * List 형의 값을 가리키는 변수는 메서드 내부에 선언된 stooges 뿐이다.
     * 메서드 내부에서 선언된 변수는 항상 스레드 내부에 종속돼있다.
     * Vector 객체에 add하는 부분과 toString을 호출하는 부분을 더해 총 4번 락을 잡았다 놓았다 반복하게된다.
     * 여기서 JVM은 stooges 변수가 메서드 외부에 유출된 적이 없다는 것을 판단하고 락을 4번이나 잡았다 놓았다 반복하는 과정 없이 빠르게 실행시킨다.
     * @return
     */
    public String getStoogeNames() {
        List<String> stooges = new Vector<String>();
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");

        return stooges.toString();
    }
}
