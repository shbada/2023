package tobyspring.helloboot.decorator;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tobyspring.helloboot.di.HelloService;

/**
 * HelloController 가 HelloDecorator 을 의존하게한다.
 * HelloDecorator 이 HelloService 구현 클래스에 의존하게한다.
 * HelloController -> HelloDecorator (HelloService 타입) -> HelloService (HelloService 타입)
 *
 * HelloService 타입의 빈이 2개이므로, @Primary 어노테이션을 붙이자.
 * 그럼 빈이 2개일때 해당 클래스를 우선적으로 적용한다.
 */
@Service
@Primary
public class HelloDecorator implements HelloService {
    // HelloService 를 의존한다.
    private final HelloService helloService;

    public HelloDecorator(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        return "*" + helloService.sayHello(name) + "*";
    }
}
