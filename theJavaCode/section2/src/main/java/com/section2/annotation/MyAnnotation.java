package com.section2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE}) // 어노테이션 선언위치 지정
public @interface MyAnnotation {
    // 값들을 가질 수 있다.
    String name(); // 전부 public

    String value() default "hello";

    int number() default 100;
}
