package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(String name) {
        SimpleHelloService simpleHelloService = new SimpleHelloService();

        // null 이라면 예외를 던진다.
        return simpleHelloService.sayHello(Objects.requireNonNull(name));
    }
}
