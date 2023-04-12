package me.whiteship.chapter01.item02.freeze;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Person {
    // 객체 얼리기 - final (선언 후 변경 불가능)
    private final String name;

    private final int birthYear;

    // 원소가 추가되는게 불가능한게 아니라, reference 변경이 불가능하다는 것
    private final List<String> kids;

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.kids = new ArrayList<>();
    }

    public static void main(String[] args) {
        Person person = new Person("keesun", 1982);

//        final Person person2 = new Person("keesun", 1982);
//        person2 = new Person("seohae", 9999);
    }
}
