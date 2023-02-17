package com.concurrency.chapter04.예제4_11_값변경가능_스레드안전;

import net.jcip.annotations.*;

/**
 * SafePoint
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 값 변경이 가능하고 스레드 안정성도 확보한 클래스
 *
 * 클래스의 스레드 안정성을 내부 상태 변수에 위임하고, 안정성을 위임받은 상태 변수의 값을
 * 외부 프로그램이 변경할 수 있도록 외부에 공개하고자 한다면?
 * 만약 필드가 0보다 크거나 같은 값만 가능한 조건이 있을때, 외부에서 0보다 작은 값으로 셋팅할 수가 있다.
 *
 * 상태 변수가 스레드 안전하고, 클래스 내부에서 상태 변수의 값에 대한 의존성을 갖고 있지 않고,
 * 상태 변수에 대한 어떤 연산을 수행하더라도 잘못된 상태에 이를 기능성이 없다면,
 * 해당 변수는 외부에 공개해도 안전하다.
 */
@ThreadSafe
public class SafePoint {
    @GuardedBy("this") private int x, y;

    /*
        좌표의 x, y 값을 두칸짜리 배열로 한꺼번에 가져갈 수 있다.
     */
    private SafePoint(int[] a) {
        this(a[0], a[1]);
    }

    public SafePoint(SafePoint p) {
        this(p.get());
    }

    public SafePoint(int x, int y) {
        this.set(x, y);
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }

    /*
    x, y 둘을 변경하는 setX, setY를 각각 만든다면, 차량의 위치가 바뀌는 상황이 발생할 수도 있다.
     */
    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
