package me.whiteship.chapter01.item01.step05_interface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListQuiz {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList();
        numbers.add(100);
        numbers.add(20);
        numbers.add(44);
        numbers.add(3);

        System.out.println(numbers);

        /* 내림차순 */
        Comparator<Integer> desc = (o1, o2) -> o2 - o1;

        /* 다시 오름차순 */
        numbers.sort(desc.reversed()); // reverse() : default 메서드가 추가됨

        System.out.println(numbers);
    }
}
