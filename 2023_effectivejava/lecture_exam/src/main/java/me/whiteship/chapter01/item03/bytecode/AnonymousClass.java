package me.whiteship.chapter01.item03.bytecode;

import java.util.function.IntBinaryOperator;

public class AnonymousClass {
    public static void main(String[] args) {
        IntBinaryOperator plus = new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        };
    }
}
