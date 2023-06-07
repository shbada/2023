package me.whiteship.chapter02.item13.test.inheritance;

import org.checkerframework.checker.units.qual.C;

public class Cat {
    public static void main(String[] args) {
        Cat cat = new Cat();

        try {
            cat.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
