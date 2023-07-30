package me.whiteship.chapter04.item19.constructor;

import java.time.Instant;

// 생성자에서 호출하는 메서드를 재정의했을 때의 문제를 보여준다. (126쪽)
public final class Sub extends Super {
    // 초기화되지 않은 final 필드. 생성자에서 초기화한다.
    private final Instant instant;

    Sub() {
        // [해당 코드의 문제점]
        // 기본적으로 상위클래스의 생성자를 호출하고있음 (Super();)
        // 부모클래스 생성자안에서 호출하는 재정의 가능한 overrideMe()가 그래서 먼저 호출되는데,
        // 이때는 instant에 now()가 들어가기 전이므로 null이다.
        // 그 이후부터 instant가 제대로 값이 출력된다.
        instant = Instant.now();
    }

    // 재정의 가능 메서드. 상위 클래스의 생성자가 호출한다.
    @Override public void overrideMe() {
        System.out.println(instant);
    }

    public static void main(String[] args) {
        Sub sub = new Sub();
        sub.overrideMe();
    }
}
