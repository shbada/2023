package me.whiteship.chapter04.item17.part2;

import java.awt.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class BigIntExample {

    public static void main(String[] args) {
        BigInteger ten = BigInteger.TEN;
        BigInteger minusTen = ten.negate();

        final Set<Point> points = new HashSet<>();
        Point firstPoint = new Point(1, 2);
        points.add(firstPoint);

    }
}
