package com.concurrency.chapter04.예제4_10_위임의_문제점;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.*;

/**
 * NumberRange
 * <p/>
 * Number range class that does not sufficiently protect its invariants
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 숫자 범위를 나타내는 클래스, 의존성 조건을 정확하게 처리하지 못하고있다.
 *
 * NumberRange 클래스는 스레드 안정성을 확보하지 못했다.
 * lower, upper라는 변수의 의존성 조건을 100% 만족시키지 못한다.
 * 메서드에서 각 확인중인 조건 체크는 제대로 동작하지 않을 수도 있다.
 * -> setLower, setUpper 메서드 양쪽 모두 비교문을 사용하지만, 비교문을 단일 연산으로 처리할 수 있도록 동기화 기법을 적용하지 않고있다.
 * 만약, 숫자 범위가 (0, 10)인 상태에서 스레드 A가 setLower(5)를 호출했다고 하자.
 * 그와 동시에 스레드 B가 setUpper(4)를 호출했다고 하자.
 * 양쪽 모두 스레드 비교문을 통과해서 작은 숫자로 5, 큰 숫자를 4로 지정하는 상태에 이를 수 있다.
 * 결과적으로 (5, 4)라는 숫자 범위는 의존성 조건에 위배된다.
 *
 * NumberRange 클래스는 upper와 lower 변수 주변에 락을 사용하는 등의 방법을 적용하여 동기화하면 쉽게 의존성 조건을 충족시킬 수 있다.
 * 그리고 lower, upper 변수를 외부에 공개해 다른 프로그램에서 의존성 조건을 무시하고 값을 변경하는 사태가 일어나지 않도록 적절하게 캡슐화해야한다.
 *
 * 이 클래스처럼 두개 이상의 변수를 사용하는 복합 연산 메서드를 갖고 있다면 위임 기법만으로는 스레드 안정성을 확보할 수 없다.
 * 이런 경우에는 내부적으로 락을 활용해서 복합 연산이 단일 연산으로 처리되도록 동기화해야한다.
 *
 * 스레드 안전한 클래스를 가져다 사용했음에도 스레들 안정성을 확보하지 못한 이유는?
 * 특정 변수가 다른 상태 변수와 아무런 의존성이 없는 상황이라면 해당 변수를 volatile로 선언해도 스레드 안정성에는 지장이 없다는 규칙이 있다.
 * 이 규칙과 굉장히 비슷하다.
 */

public class NumberRange {
    /* 조건 : 첫번째 숫자가 두번째 숫자보다 작거나 같아야한다. */
    // -> 의존성 조건 : lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        // 주의 - 안전하지 않은 비교문
        if (i > upper.get())
            throw new IllegalArgumentException("can't set lower to " + i + " > upper");
        lower.set(i);
    }

    public void setUpper(int i) {
        // 주의 - 안전하지 않은 비교문
        if (i < lower.get())
            throw new IllegalArgumentException("can't set upper to " + i + " < lower");

        upper.set(i);
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }
}

