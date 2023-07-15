package me.whiteship.chapter04.item16.dimension;

import java.awt.*;
import java.util.Comparator;
import java.util.function.BiFunction;

public class DimensionExample {

    public static void main(String[] args) {
        Button button = new Button("hello button");
        button.setBounds(0, 0, 20, 10);

        // 내부 필드를 public으로 노출한 클래스 : Dimension
        // 인스턴스 copy의 단점 - 불필요한 인스턴스를 계속 생성 (성능이슈)
        Dimension size = button.getSize();

        System.out.println(size.height);
        System.out.println(size.width);

        doSomething(size);
    }

    private static void doSomething(Dimension size) {
        System.out.println(size.height); // 이렇게 하지 말라

        // 복사본을 만들어라
        // 이렇게 복사하는건 성능상으로 이슈가 크진 않겠지만, 이렇게 해야한다고 생각해야한다는게 불편
        Dimension copy = new Dimension();
        copy.width = size.width;
    }

}
