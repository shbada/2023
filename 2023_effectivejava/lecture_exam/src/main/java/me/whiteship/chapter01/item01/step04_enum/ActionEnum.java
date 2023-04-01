package me.whiteship.chapter01.item01.step04_enum;

import me.whiteship.chapter01.item01.step06_serviceProvider.App;
import me.whiteship.chapter01.item01.step01.OrderStatus;

import java.util.Arrays;

public enum ActionEnum {
    ACTION_1(new App()) // 인스턴스가 단 하나만 생성된다.
    , ACTION_2(new App());

    private final App action;

    ActionEnum(App action) {this.action = action;}

    public static void main(String[] args) {
        // 순회하면서 출력하기
        Arrays.stream(OrderStatus.values()).forEach(System.out::println);

        // Enum은 ==로 비교할 수 있다. JVM 내에 단 하나의 인스턴스만 있기 때문
        // Enum 만을 담고있을땐 EnumMap, EnumSet 을 사용하는것이 성능적으로 좋다.
    }
}