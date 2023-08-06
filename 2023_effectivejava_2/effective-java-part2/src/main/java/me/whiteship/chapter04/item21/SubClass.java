package me.whiteship.chapter04.item21;

public class SubClass extends SuperClass implements MarkerInterface {

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        // SuperClass 의 hello()는 private 임에도 이 클래스의 hello()를 호출하려고한다.
        // 그래서 오류가 발생한다. (인터페이스의 hello()를 호출하지 않고 클래스의 메서드를 우선적으로 호출한다.)
        // 인터페이스의 hello()가 있기 때문에 컴파일오류로 안잡힌다.
        // 약간 버그성 오류다. 결국 private hello()를 호출해서 서버오류가 발생하는데, 인터페이스의 default hello() 때문에 컴파일 오류로 못잡는다.
        // 인터페이스의 default hello()를 주석처리하면 컴파일오류가 나온다.
        subClass.hello();
    }

}
