package me.whiteship.chapter02.item13.treeset;

import me.whiteship.chapter02.item13.PhoneNumber;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * TreeSet
 * - 엘리먼트를 추가한 순서는 중요하지 않다.
 * - 엘리먼트가 지닌 자연적인 순서에 따라 정렬한다.
 * - 오름차순으로 정렬한다.
 * - 스레드 안전하지 않다. (이진 검색 트리, 레드 블랙 트리)
 */
public class TreeSetExample {

    public static void main(String[] args) {
//        TreeSet<Integer> numbers = new TreeSet<>();
//        numbers.add(10);
//        numbers.add(4);
//        numbers.add(6);

        // 동기화 - Collections.synchronizedSet(numbers) 로 감싸면 됨 
        TreeSet<PhoneNumber> numbers = new TreeSet<>(Comparator.comparingInt(PhoneNumber::hashCode));
        Set<PhoneNumber> phoneNumbers = Collections.synchronizedSet(numbers);
        phoneNumbers.add(new PhoneNumber(123, 456, 780));
        phoneNumbers.add(new PhoneNumber(123, 456, 7890));
        phoneNumbers.add(new PhoneNumber(123, 456, 789));

        // natural order 을 모르면 못넣음 (ClassCastException 발생함) - Comparable 을 구현해야한다.
        // 1) PhoneNumber 을 Comparable 을 구현하게끔 하거나,
        // 2) 정렬할 수 있는 natural order 방법만 넘겨주던가
        for (PhoneNumber number : numbers) {
            System.out.println(number);
        }
    }
}
