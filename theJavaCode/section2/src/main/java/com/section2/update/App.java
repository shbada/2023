package com.section2.update;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class App {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<Book> bookClass = Book.class;
        Constructor<Book> constructor = bookClass.getConstructor(null);
        Book book = constructor.newInstance();
        // String 파라미터 1개를 받는 생성자
        Constructor<Book> constructor2 = bookClass.getConstructor(String.class);
        Book book2 = constructor2.newInstance("book");

        // 값 셋팅
        Field a = Book.class.getDeclaredField("A");
        System.out.println(a.get(null));
        a.set(null, "AAAAA");
        System.out.println(a.get(null));

        // 특정한 인스턴스에 해당하는 필드인데 null로는 가져올 수 없다.
        Field b = Book.class.getDeclaredField("B");

        b.setAccessible(true); // private 접근 가능
        System.out.println(b.get(book));
    }
}
