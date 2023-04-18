package me.whiteship.chapter01.item04;

/*
UtilityClass을 추상클래스로 만들고, 이를 상속하는 하위클래스 생성
- 인스턴스 생성이 가능하다.

자식클래스의 인스턴스 생성시, 상위클래스의 생성자를 기본적으로 호출함
 */
public class DefaultUtilityClass extends UtilityClass {

    public static void main(String[] args) {
        DefaultUtilityClass utilityClass = new DefaultUtilityClass();
        // 권장하지 않는 방법으로 상위클래스의 static 메서드 호출이 가능
        utilityClass.hello();
    }
}
