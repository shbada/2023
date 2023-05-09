package com.section2.annotation;

import com.section2.reflection.Book;
import com.section2.reflection.MyBook;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        /**
         * 조회가 안된다.
         * 기본적으로 annotation은 주석(comment)와 마찬가지다.
         * 근본적으로 주석이랑 같은 취급을 받으므로, @MyAnnotation 정보가 바이트코드를 로딩했을때 메모리상에 남지않는다.
         * 만약 런타임까지 유지하고싶다면, @Retention을 어노테이션 클래스 내에 선언해야한다.
         */
        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);

        // 하위클래스 MyBook 에서 Book의 어노테이션 가져오기
        // @Inherited 를 어노테이션 클래스 내에 선언하면 된다.
        Arrays.stream(MyBook.class.getAnnotations()).forEach(System.out::println);

        // @Inherited 있을때 하위클래스 MyBook에만 있는 어노테이션 가져오기
        Arrays.stream(MyBook.class.getDeclaredAnnotations()).forEach(System.out::println);

    }
}
