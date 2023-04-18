package me.whiteship.chapter01.item04;

import org.springframework.context.annotation.AnnotationConfigUtils;

/*
인스턴스 생성 방지
1) abstract 추상 클래스 만들기
- 하위클래스 생성자가 상위 클래스의 생성자를 호출하기 때문에 충분하지 않다.
2) private 생성자
 */
public class UtilityClass {

    /**
     * 이 클래스는 인스턴스를 만들 수 없습니다.
     */
//    private UtilityClass() {
//        throw new AssertionError();
//    }

    public static String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        // static 메서드 호출
        String hello = UtilityClass.hello();

        // 인스턴스를 가지고 static 메서드 호출이 가능하긴하다. (권장X)
        // 매우 불필요
        // 인스턴스 생성 방지 - private 생성자
        UtilityClass utilityClass = new UtilityClass();
        utilityClass.hello();


    }
}
