package me.whiteship.chapter01.item03.methodreference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Person {

    LocalDate birthday;

    public Person() {

    }

    public Person(LocalDate birthday) {
        this.birthday = birthday;
    }

    public static int compareByAge(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    public int compareByAge2(Person b) {
        // 비교 대상은 자기 자신
        return this.birthday.compareTo(b.birthday);
    }

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person(LocalDate.of(1982, 7, 15)));
        people.add(new Person(LocalDate.of(2011, 3, 2)));
        people.add(new Person(LocalDate.of(2013, 1, 28)));

        /*
         * 익명 내부 클래스
         */
        people.sort(new Comparator<Person>() {
            @Override
            public int compare(Person a, Person b) {
                return a.birthday.compareTo(b.birthday);
            }
        });

        /*
         * 람다
         */
        people.sort((a, b) -> a.birthday.compareTo(b.birthday));

        /*
         * 람다 - 메서드 참조
         */
        people.sort(Person::compareByAge);

        // 임의의 객체에 대한 메서드참조
        // 첫번째 인자가 자기 자신
        people.sort(Person::compareByAge2);

        // 인자가 있는 생성자, 없는 생성자 중 어떤걸 호출해야할지 모른다.
//        Person::new

        // Function 로 받으므로 인자가 있는 Person 생성자가 호출된다.
        Function<LocalDate, Person> aNew = Person::new;

        // 인자가 없는 생성자는 어떻게 호출할까?
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthday.getYear();
    }

}
