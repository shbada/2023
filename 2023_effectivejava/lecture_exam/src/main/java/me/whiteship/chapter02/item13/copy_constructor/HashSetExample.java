package me.whiteship.chapter02.item13.copy_constructor;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class HashSetExample {

    public static void main(String[] args) {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("keesun");
        hashSet.add("whiteship");
        System.out.println("HashSet: " + hashSet);

        /** 실제로는 생성자를 씀, 생성자를 통해서 copy */
        // 생성자 파라미터로 상위타입 Collection 으로 받아서 하위타입을 모두 받을 수 있다.
        // 그래서 원하는 하위타입으로 변환할 수 있다.
        // clone으로는 할 수 없음
        Set<String> treeSet = new TreeSet<>(hashSet);

        System.out.println("TreeSet: " + treeSet);
    }
}
