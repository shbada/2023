package me.whiteship.chapter02.item14.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        Integer[] arr2 = {11, 5, 30, 40, 35, 80, 60};

        // Collections 메소드 사용
        Arrays.sort(arr2, Collections.reverseOrder());
        Arrays.sort(arr2);

        // 직접 구현도 가능
        Arrays.sort(arr2, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return i2 - i1;
            }
        });

        for(int i : arr2) {
            System.out.println(i);
        }
    }
}
