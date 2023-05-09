package com.section2.reflection;

import java.util.Arrays;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        // 1
        Class<Book> bookClass = Book.class;

        // 2
        Book book = new Book();
        Class<? extends Book> aClass = book.getClass();

        // 3
        Class<?> aClass1 = Class.forName("com.section2.reflection.Book");

        // field (public)
        Arrays.stream(bookClass.getFields()).forEach(System.out::println);

        // field all
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

        // field name
        System.out.println(bookClass.getDeclaredField("a"));

        // field get value
        Arrays.stream(bookClass.getFields()).forEach(f -> {
            try {
                f.setAccessible(true);
                System.out.printf("%s %s\n", f, f.get(book));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        // method (정의 + 상속받은 모든 메서드)
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);

        // 생성자
        Arrays.stream(bookClass.getDeclaredConstructors()).forEach(System.out::println);

        // 상위 클래스
        System.out.println(MyBook.class.getSuperclass());

        // 상위 인터페이스
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);
    }
}
