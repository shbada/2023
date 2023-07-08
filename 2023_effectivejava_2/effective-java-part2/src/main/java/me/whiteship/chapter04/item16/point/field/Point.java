package me.whiteship.chapter04.item16.point.field;

public class Point {
    public double x;
    public double y;

    public static void main(String[] args) {
        Point point = new Point();
        point.x = 10;
        point.y = 20;

        System.out.println(point.x);
        System.out.println(point.y);
    }
}
