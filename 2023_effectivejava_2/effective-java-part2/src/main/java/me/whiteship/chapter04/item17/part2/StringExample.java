package me.whiteship.chapter04.item17.part2;

public class StringExample {

    public static void main(String[] args) {
        // String 자체는 불변
        String name = "whiteship";

        StringBuilder nameBuilder = new StringBuilder(name);
        nameBuilder.append("keesun"); // builder의 내부 값에 추가 (인스턴스를 재생성하지않음)
    }
}
