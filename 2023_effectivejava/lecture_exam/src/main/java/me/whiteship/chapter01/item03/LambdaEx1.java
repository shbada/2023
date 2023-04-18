package me.whiteship.chapter01.item03;

import java.util.function.IntBinaryOperator;

public class LambdaEx1 {
    public static void main(String[] args) {
        LambdaEx1 lambdaEx1 = new LambdaEx1();
        IntBinaryOperator plus = lambdaEx1.plus();
        plus.applyAsInt(1, 3);
    }

    private IntBinaryOperator plus() {
        IntBinaryOperator plus = (x, y) -> {
            System.out.println(this);
            return x + y;
        };

        return plus;
    }
}
