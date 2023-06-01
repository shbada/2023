package me.whiteship.chapter02.item11;

import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = 3;
        Objects.hash(a, b, c);
    }
}
