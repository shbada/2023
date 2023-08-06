package me.whiteship.chapter04.item24.memberclass;

public class OutterClass {

    private int number = 10;

    void printNumber() {
        InnerClass innerClass = new InnerClass();
    }

    private class InnerClass {
        void doSomething() {
            System.out.println(number);
            // OutterClass.this. : OutterClass의 인스턴스
            OutterClass.this.printNumber();
        }
    }

    public static void main(String[] args) {
        // 비정적이라 아우터클래스를 먼저 만들고 이너클래스 인스턴스 생성 가능
        InnerClass innerClass = new OutterClass().new InnerClass();
        innerClass.doSomething();
    }

}
