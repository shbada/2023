package me.whiteship.chapter02.item10;

import java.util.ArrayList;
import java.util.List;

// 단순한 불변 2차원 정수 점(point) 클래스 (56쪽)
public class Point {

    private final int x; // final 로 해서 immutable하게 만든다.
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public boolean equals(Object o) {
        // 자기자신과 같은지 확인 (객체와 동일성) - 반사성
        if (this == o) {
            return true;
        }

        if (!(o instanceof Point)) {
            return false;
        }

        Point p = (Point) o;
        return p.x == x && p.y == y;
    }

    public static void main(String[] args) {
        Point point = new Point(1, 2);
        List<Point> points = new ArrayList<>();
        points.add(point);

        // equals()를 논리적 동치성을 판단하도록 재정의해야 true다.
        System.out.println(points.contains(new Point(1, 2)));
    }

    // 잘못된 코드 - 리스코프 치환 원칙 위배! (59쪽)
    // 상위타입일때, 하위타입일때 onUnitCircle() 메서드가 true, flase로 다르게 나온다.
//    @Override public boolean equals(Object o) {
//        if (o == null || o.getClass() != getClass())
//            return false;
//        Point p = (Point) o;
//        return p.x == x && p.y == y;
//    }

    // 아이템 11 참조
    @Override public int hashCode()  {
        return 31 * x + y;
    }
}
