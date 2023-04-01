package me.whiteship.chapter01.item01.step03;

/**
 * interface 안에서의 메서드는 접근제한자 선언하지 않으면 public 이다.
 * (클래스는 package-private)
 */
public interface HelloService {

    String hello();

    static String hi() {
        prepareMessage();
        return "hi";
    }

    static private void prepareMessage() {
    }

    static String hi1() {
        prepareMessage();
        return "hi";
    }

    static String hi2() {
        prepareMessage();
        return "hi";
    }

    /**
     * default 메서드
     * @return
     */
    default String bye() {
        return "bye";
    }
}
