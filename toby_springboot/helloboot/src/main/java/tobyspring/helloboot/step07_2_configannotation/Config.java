package tobyspring.helloboot.step07_2_configannotation;

import org.springframework.context.annotation.Configuration;

// @Component // 클래스가 빈으로 등록되면서 Bean 어노테이션이 붙은 팩토리 메서드들이 동작하겠다.
@Configuration // 메타 어노테이션으로 @Component 를 가지고있다. 그러므로 이것을 사용하는게 더 좋다.
// @Configuration 이 붙은 클래스를 발견하면 그 안의 @Bean 어노테이션을 가진 팩토리 메서드들을 빈으로 등록한다.
public class Config {
//    @Bean
//    public ServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }

//    @Bean
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet();
//    }

}
