package me.whiteship.chapter04.item24.staticmemberclass;

/**
 * 정적 멤버 클래스
 */
public class OutterClass {

    private static int number = 10;

    static private class InnerClass {
        void doSomething() {
            // 바깥 클래스의 static 인스턴스에 접근 가능
            // OutterClass 인스턴스를 필요로 하지 않음 (독립적)
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        innerClass.doSomething();

    }
}
