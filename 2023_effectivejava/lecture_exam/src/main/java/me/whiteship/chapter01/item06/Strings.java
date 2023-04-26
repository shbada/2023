package me.whiteship.chapter01.item06;

public class Strings {

    public static void main(String[] args) {
        String hello = "hello";

        // 이 방법은 권장하지 않습니다.
        /*
        JVM은 내부적으로 문자열을 pool에서 캐싱하고있다.
        한번 만들어진 문자열을 담아두고, 동일한 문자열을 생성하려고하면 이미 만들어놓은 상수 풀 안의 동일한 문자열을
        참조하는 방법으로 관리한다.
         */
        String hello2 = new String("hello");

        String hello3 = "hello";

        System.out.println(hello == hello2);
        System.out.println(hello.equals(hello2));
        System.out.println(hello == hello3); // true
        System.out.println(hello.equals(hello3));
        System.out.println(hello2 == hello3); // false
        System.out.println(hello2.equals(hello3));
    }
}
