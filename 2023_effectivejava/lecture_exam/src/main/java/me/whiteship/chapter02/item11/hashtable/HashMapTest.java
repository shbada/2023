package me.whiteship.chapter02.item11.hashtable;

import me.whiteship.chapter02.item11.guava.PhoneNumber;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    public static void main(String[] args) {

        PhoneNumber number1 = new PhoneNumber(123, 456, 7890);
        PhoneNumber number2 = new PhoneNumber(123, 456, 7890);

//         TODO 같은 인스턴스인데 다른 hashCode
        System.out.println(number1.equals(number2));
        System.out.println(number1.hashCode());
        System.out.println(number2.hashCode());

        Map<PhoneNumber, String> map = new HashMap<>();
        map.put(number1, "keesun");
        map.put(number2, "whiteship");

        /*
            넘긴 key에 대한 hashcode 값을 먼저 가져오고, hash에 해당하는 버킷에 들어있는 오브젝트를 꺼내온다.
         */
        String notFound = map.get(new PhoneNumber(123, 456, 7890));

        /** 다른 인스턴스인데 같은 hashCode를 쓴다면? */
        PhoneNumber number3 = new PhoneNumber(456, 789, 1111);

        System.out.println(number1.equals(number3));
        /*
            다른 인스턴스인데 hashcode 가 같다. hash 충돌 발생 - 같은 버킷 안에 연결리스트 상태로 저장된다.
            O(1) -> O(n) (연결리스트) : 효율성 떨어짐
        */
        System.out.println(number1.hashCode());
        System.out.println(number3.hashCode());
        String getNumber1 = map.get(number1);

        String s = map.get(number2);
        System.out.println(s);
    }
}
