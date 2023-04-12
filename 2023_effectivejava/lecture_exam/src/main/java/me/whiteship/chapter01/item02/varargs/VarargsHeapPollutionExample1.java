package me.whiteship.chapter01.item02.varargs;

import java.util.Arrays;
import java.util.List;

public class VarargsHeapPollutionExample1 {
    public static void main(String[] args) {
        List<String> strings1 = Arrays.asList("첫 요소");
        List<String> strings2 = Arrays.asList("첫 요소");
        doSomthing(strings1, strings2);
    }

    private static void doSomthing(List<String> ... stringLists) { // 가변인자는 배열을 하나 만든다. -> List<String> 배열
        List<Integer> intList = Arrays.asList(42);

        // List<String> 가변인자를 Object[] 배열 변수로 초기화
        Object[] objects = stringLists;

        // 인덱스 0에 List<Integer> 타입의 변수 설정
        objects[0] = intList; // 힙 오염 발생

        String s = stringLists[0].get(0); // ClassCastException
    }
}
