package me.whiteship.chapter01.item01.step06_serviceProvider;

/**
 * 서비스 제공자 인터페이스
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
