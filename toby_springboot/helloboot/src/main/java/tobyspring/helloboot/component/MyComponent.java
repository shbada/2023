package tobyspring.helloboot.component;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target(ElementType.ANNOTATION_TYPE) // 메타어노테이션이 가능하게하기 위해 선언
@Retention(RetentionPolicy.RUNTIME) // annotation이 언제까지 유지될것인가?
@Target(ElementType.TYPE) // annotation 적용할 대상 지정
@Component // meta annotation 으로 @Component 추가
public @interface MyComponent {
}
