package me.whiteship.chapter01.item01.step05_interface;

/**
 * interface 안에서의 메서드는 접근제한자 선언하지 않으면 public 이다.
 * (클래스는 package-private)
 */
public interface HelloService {
    String hello();

    /**
     * 인터페이스 내 정적 메서드
     * private static 메서드 선언 가능 (private 필드는 불가능)
     * @return
     */
    static String hi() {
        prepareMessage();
        return "hi";
    }

    /**
     * 인터페이스 내 default 메서드
     * 메서드 구현체를 가질 수 있다.
     * @return
     */
    default String bye() {
        return "bye";
    }

    private static void prepareMessage() {
    }

    static String hi1() {
        prepareMessage();
        return "hi";
    }

    static String hi2() {
        prepareMessage();
        return "hi";
    }
}
