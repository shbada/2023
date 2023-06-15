package me.whiteship.chapter02.item14.test;

import java.util.Arrays;

public class ArraysTest {

    public static void main(String[] args) {

        MyInteger[] arr = new MyInteger[10];

        // 객체 배열 초기화 (랜덤 값으로)
        for(int i = 0; i < 10; i++) {
            arr[i] = new MyInteger((int)(Math.random() * 100));
        }

        // 정렬 이전
        System.out.print("정렬 전 : ");
        for(int i = 0; i < 10; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();

        Arrays.sort(arr);

        // 정렬 이후
        System.out.print("정렬 후 : ");
        for(int i = 0; i < 10; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();
    }

}

class MyInteger implements Comparable<MyInteger> {
    int value;

    public MyInteger(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(MyInteger o) {
        System.out.println("this.value : " + this.value);
        System.out.println("o.value : " + o.value);
        System.out.println("-");
        return this.value - o.value;
    }

}

//class MyInteger{
//    int value;
//
//    public MyInteger(int value) {
//        this.value = value;
//    }
//}