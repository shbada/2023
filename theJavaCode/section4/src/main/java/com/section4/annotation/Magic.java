package com.section4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
CLASS 까지 유지할 필요가 없다.
-> 바이트 코드에서도 유지하겠다는 건데, 이는 필요가 없다. 런타임에도 필요없다.

오로지 소스 레벨에서만 필요하다.
어노테이션 프로세서가,
소스 레벨에서만 읽어서 적절한 소스코드를 만들고 컴파일 하고, 새로운 클래스인 MajicMoja 를 만든다.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE) // interface, class, Enum
public @interface Magic {
}
