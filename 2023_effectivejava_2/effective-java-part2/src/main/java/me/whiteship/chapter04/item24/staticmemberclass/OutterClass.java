package me.whiteship.chapter04.item24.staticmemberclass;

public class OutterClass {

    private static int number = 10;

    static private class InnerClass {
        void doSomething() {
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        innerClass.doSomething();

    }
}
