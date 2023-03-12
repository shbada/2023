package tobyspring.helloboot.step07_2_configannotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Retention(RetentionPolicy.CLASS) // 컴파일된 클래스 파일까지는 살아있지만, 런타임 메모리로 로딩할때는 정보가 사라진다.
@Retention(RetentionPolicy.RUNTIME) // 런타임때까지 유지된다.
@Target(ElementType.TYPE) // class, interface, enum 3가지 종류의 대상에게 부여할 수 있다.
@Configuration
@ComponentScan
//@Import({ Config.class, DispatcherServletConfig.class, TomcatWebServerConfig.class }) /* package 가 달라도 scan 대상으로 잡힌다. */
@EnableMyAutoConfiguration
public @interface MySpringBootApplication {
}
