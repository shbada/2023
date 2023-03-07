package tobyspring.helloboot.di;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloController {
    private final HelloService helloService; // final : 한번 할당 후, 변경되지 않음을 보장

    // HelloService 타입의 빈을 찾는데, SimpleHelloService 가 Application_v5에서 빈으로 등록되어서 찾아진다.
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello(String name) {
//        SimpleHelloService helloService = new SimpleHelloService();

        // null 이라면 예외를 던진다.
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}