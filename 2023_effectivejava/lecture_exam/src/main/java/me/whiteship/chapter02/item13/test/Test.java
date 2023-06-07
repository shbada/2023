package me.whiteship.chapter02.item13.test;

import com.sun.tools.javac.Main;

public class Test {
    public static void main(String[] args) {
        Target target = new Target();
        target.setName("test");
        target.setValue("vavavava");

        // 'clone()' has protected access in 'java.lang.Object
        target.clone();
    }
}
