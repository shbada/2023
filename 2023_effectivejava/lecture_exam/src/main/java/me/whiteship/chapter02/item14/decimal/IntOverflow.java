package me.whiteship.chapter02.item14.decimal;

public class IntOverflow {

    public static void main(String[] args) {
        System.out.println(-2147483648 - 10);

        // 나 자신 - 대상 값으로 계산하면 overFlow 가능성 있을 수 있음
        System.out.println(Integer.compare(-2147483648, 10));
    }
}
