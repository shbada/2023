package me.whiteship.chapter02.item14.interitance;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Point의 Comparable을 구현하고싶겠지만 이미 부모클래스에서 구현중이므로 구현할 수 없음
 * 그리고 compareTo(NamedPoint point)로 선언 한다해도 이 메서드는 쓰이지않음
 * (상속에서 상위타입으로 쓰더라도 하위 타입으로 쓰게되는 다형성이 적용 안됨)
 * @Override 불가능하고, 없애고 구현한다해도 다형성 적용이 안됨
 */
public class NamedPoint extends Point {

    final private String name;

    public NamedPoint(int x, int y, String name) {
        super(x, y);
        this.name = name;
    }

    @Override
    public String toString() {
        return "NamedPoint{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public static void main(String[] args) {
        NamedPoint p1 = new NamedPoint(1, 0, "keesun");
        NamedPoint p2 = new NamedPoint(1, 0, "whiteship");

        /**
         * 별도의 Cpmparator 제공
         * 추천하지 않는 방법
         *
         * 추천하는 방법 - /composition 패키지
         */
        Set<NamedPoint> points = new TreeSet<>(new Comparator<NamedPoint>() {
            @Override
            public int compare(NamedPoint p1, NamedPoint p2) {
                int result = Integer.compare(p1.getX(), p2.getX());
                if (result == 0) {
                    result = Integer.compare(p1.getY(), p2.getY());
                }
                if (result == 0) {
                    result = p1.name.compareTo(p2.name);
                }
                return result;
            }
        });

        points.add(p1);
        points.add(p2);

        System.out.println(points);
    }

}
