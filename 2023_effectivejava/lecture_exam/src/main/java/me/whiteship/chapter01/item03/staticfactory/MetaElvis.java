package me.whiteship.chapter01.item03.staticfactory;

import java.util.Objects;

// 코드 3-2 제네릭 싱글톤 팩토리 (24쪽)
public class MetaElvis<T> { // scope : 인스턴스

    private static final MetaElvis<Object> INSTANCE = new MetaElvis<>();

    private MetaElvis() { }

    /* 클래스 선언 T가 아니다, E로 바꿔도된다. scope : static */
    @SuppressWarnings("unchecked")
    public static <E> MetaElvis<E> getInstance() {
        return (MetaElvis<E>) INSTANCE;
    }

    /* 클래스 선언 T */
    public void say(T t) {
        System.out.println(t);
    }

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }

    public static void main(String[] args) {
        /*
        인스턴스는 동일하다.
        각 원하는 타입을 갖는다.
         */
        MetaElvis<String> elvis1 = MetaElvis.getInstance();
        MetaElvis<Integer> elvis2 = MetaElvis.getInstance();
        System.out.println(elvis1);
        System.out.println(elvis2);
        elvis1.say("hello"); // class T type
        elvis2.say(100);

        System.out.println(elvis1.equals(elvis2));
//        System.out.println(elvis1 == elvis2); // 타입이 다르므로 == 로 비교 불가능
    }

}
