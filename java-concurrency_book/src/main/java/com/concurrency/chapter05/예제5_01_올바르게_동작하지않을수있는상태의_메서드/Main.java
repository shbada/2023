package com.concurrency.chapter05.예제5_01_올바르게_동작하지않을수있는상태의_메서드;

import java.util.Vector;

/**
 * getLast(), deleteLast() 두개를 호출하여 사용하는 외부 프로그램의 입장에서 위험한 코드다.
 * A -> size:10 -> remove:9
 * B -> size:10            -> get:9 -> 오류
 */
public class Main {
    public static Object getLast(Vector list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    public static void deleteLast(Vector list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }

    public static void main(String[] args) {
        Vector<String> list = new Vector<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        deleteLast(list);
        getLast(list);
    }
}
