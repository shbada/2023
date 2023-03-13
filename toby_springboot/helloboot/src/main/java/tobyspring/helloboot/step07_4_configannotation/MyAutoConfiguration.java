package tobyspring.helloboot.step07_4_configannotation;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// MyAutoConfiguration 가 붙어서 로딩되는 Configuration은 proxyBeanMethods을 false로 바꾼 @Configuration이 적용된다.
@Configuration(proxyBeanMethods = false) // proxy를 만들지 않는다.
public @interface MyAutoConfiguration {
}
