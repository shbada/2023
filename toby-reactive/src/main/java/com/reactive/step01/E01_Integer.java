package com.reactive.step01;

import java.util.*;

public class  E01_Integer {
    public static void main(String[] args) {
        // 리스트로 받는 방법
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // List 타입은 Iterable 의 서브타입이라는 뜻이다. (List > Collection > Iterable)
        // Iterable : for-each 루프 (데이터를 하나씩 순회할 수 있다.)
        // -> Iterable 이기 때문에 for-each 에 쓸수 있다.
        for (Integer i : list) { // for-each
            System.out.println(i);
        }

        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // 순회할때는 iterator() 를 사용하자.

        // 28:11
        /* 하고자하는 것 : 1부터 9까지의 원소를 가지고, 이 원소를 순회하는 Iterator 오브젝트를 리턴하는 Iterable */
        Iterable<Integer> iter2 = () -> new Iterator<>() {
            // Iterator 를 또 구현해야하는 이유는? Iterator 를 통해서 여러번 순회하기 위해서
            int i = 0; // 시작값
            final static int MAX = 10; // 종료값

            @Override
            public boolean hasNext() {
                return i < MAX;
            }

            @Override
            public Integer next() { // 받는쪽에서 호출하며, pull
                return ++i;
            }
        };

        /*
            java의 for-each는 컬렉션이 아닌 Iterable을 구현한 객체로 사용할 수 있는 것이다.
         */
        for (Integer i : iter2) { // for-each 사용 가능 (List, Array가 아님에도 가능)
            System.out.println(i);
        }

        for (Iterator<Integer> it = iter2.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }

        // Iterable <--상대성(duality)--> Observable
        // Iterable [Pull] : 가져오는 것 (next() 메서드로 원하는 결과값을 리턴으로 가져온다.)
        // Observable [Push] : 데이터를 주는것
        // Observable : eventSource -> event를 던진다. -> Target(Observer ;관찰자)
    }
}
