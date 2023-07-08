package me.whiteship.chapter04.item15.class_and_interfaces.member;

import java.util.Arrays;

/**
 * 외부에 공개하지 않는 구현부(item 패키지에서 알필요가 없음)
 */
class DefaultMemberService implements MemberService {

    private String name;

    /**
     * 한 클래스에서만 사용되는 클래스나 인터페이스의 경우
     * -> private static 으로 중첩 시키자
     * 그런데 왜 private static 로 해야하나?
     * -> 원래 외부 클래스와 관계가 있는게 아니기 때문에 이게 더 자연스러움
     */
    private static class PrivateStaticClass {

    }

    /**
     * private 내부 클래스는 외부 클래스의 멤버 변수에 바로 접근할 수 있음
     * 내부적으로 외부 클래스 객체의 참조를 가지고 있기 때문에
     */
    private class PrivateClass {
        void doPrint() {
            System.out.println(name);
        }
    }

    /**
     * 실행시켜보면 PrivateClass 내부에 외부 클래스 참조가 있는 것을 확인할 수 있음
     */
    public static void main(String[] args) {
        Arrays.stream(PrivateClass.class.getDeclaredFields()).forEach(System.out::println);
    }

}
