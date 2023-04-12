package me.whiteship.chapter01.item02.varargs;

import java.util.ArrayList;
import java.util.List;

public class HeapPollutionExample {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();

        addToList(stringList, 10);
        addToList(integerList, "aa");

        String str = stringList.get(0); // ClassCastException 발생
        Integer num = integerList.get(0); // ClassCastException 발생
    }

    public static void addToList(List list, Object obj) {
        list.add(obj);
    }
}
