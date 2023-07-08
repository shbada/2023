package me.whiteship.chapter05.item28.erasure;

import java.util.ArrayList;
import java.util.List;

public class MyGeneric {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("keesun");
        String name = names.get(0);
        System.out.println(name);
    }

}
