package main.java.me.whiteship.chapter04.item15.class_and_interfaces.test;

public class Test {
    public static void main(String[] args) {
        TestInterface test = new TestInterface() {};
        System.out.println(test.hashCode());
    }
}
