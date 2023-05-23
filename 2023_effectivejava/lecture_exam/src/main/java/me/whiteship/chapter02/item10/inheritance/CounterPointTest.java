package me.whiteship.chapter02.item10.inheritance;


import me.whiteship.chapter02.item10.Color;
import me.whiteship.chapter02.item10.Point;

import java.util.Set;

// CounterPoint를 Point로 사용하는 테스트 프로그램
public class CounterPointTest {
    // 단위 원 안의 모든 점을 포함하도록 unitCircle을 초기화한다. (58쪽)
    private static final Set<Point> unitCircle = Set.of(
            new Point( 1,  0), new Point( 0,  1),
            new Point(-1,  0), new Point( 0, -1));

    public static boolean onUnitCircle(Point p) {
        return unitCircle.contains(p);
    }

    /*
     // 잘못된 코드 - 리스코프 치환 원칙 위배! (59쪽) 의 equals()일 때

     리스코프 치환 원칙
     - 상위 클래스의 동작과 하위 클래스가 같아야한다.
     */
    public static void main(String[] args) {
        Point p1 = new Point(1,  0);
        Point p2 = new CounterPoint(1, 0); // Point 클래스의 자식클래스
//        Point p2 = new me.whiteship.chapter02.item10.composition.ColorPoint(1, 0, Color.RED).asPoint();

        // true를 출력한다.
        System.out.println(onUnitCircle(p1));

        // true를 출력해야 하지만, Point의 equals가 getClass를 사용해 작성되었다면 그렇지 않다.
        // CounterPoint.getClass() != Point.getClass()
        System.out.println(onUnitCircle(p2));
    }
}
