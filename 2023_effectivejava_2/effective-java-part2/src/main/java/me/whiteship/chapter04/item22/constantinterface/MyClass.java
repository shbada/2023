package me.whiteship.chapter04.item22.constantinterface;

public class MyClass implements PhysicalConstants {

    public static void main(String[] args) {
        // 인터페이스를 구현해서 PhysicalConstants.를 생략할 수 있다.
        // 안티패턴 - 인터페이스의 원래 의도를 오염시킨다.
        // 인터페이스의 의도는 타입을 정의하는 것인데, MyCalss가 PhysicalConstants 타입인가? 라고 한다면 아니다.
        // 네임스페이스 생략을 위한 구현이다. 부적절하다.
        System.out.println(BOLTZMANN_CONSTANT);
    }

}
