package me.whiteship.chapter01.item06;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class keySetTest {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Set<String> keySet1 = map.keySet();
        Set<String> keySet2 = map.keySet();
        System.out.println("Original key set: " + keySet1); // Original key set: [one, two, three]

        map.put("four", 4); // 요소 추가
        keySet1.remove("two");

        System.out.println("Updated key set keySet1: " + keySet1);
        System.out.println("key set keySet2: " + keySet2); // keySet1 의 수정내역이 keySet2에도 적용된다.
        System.out.println("Original map: " + map); // remove된 "two"가 map 안에서도 제거되었다.
    }
}
