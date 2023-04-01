package me.whiteship.chapter01.item01.step04_enum;

import me.whiteship.chapter01.item01.step02.Settings;

import java.util.EnumSet;

public class Product {

    public static void main(String[] args) {
        Settings settings1 = Settings.getInstance();
        Settings settings2 = Settings.getInstance();

        System.out.println(settings1);
        System.out.println(settings2);

        Boolean.valueOf(false);
        EnumSet.allOf(Difficulty.class);
    }
}
