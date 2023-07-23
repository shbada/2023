package me.whiteship.chapter04.item17.part2;

import java.awt.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class BigIntExample {

    public static void main(String[] args) {
        BigInteger ten = BigInteger.TEN;
        // 반대로 바꿈
        // negate() 안에서 자신의 mag 배열을 사용중
        // 배열 mag는 불변이라서 공유해서 써도 된다.
        BigInteger minusTen = ten.negate();

        // 레퍼런스 자체가 안바뀜, 요소는 바뀔수있음
        final Set<Point> points = new HashSet<>();
        Point firstPoint = new Point(1, 2);
        points.add(firstPoint);
        // firstPoint.x = 10 이런식으로 하면 변경될수있음

    }
}
