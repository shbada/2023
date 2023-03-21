package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tobyspring.helloboot.step04_component.HelloService;
import tobyspring.helloboot.step19_service.HelloJdbcService;

import java.util.Objects;

@RestController
public class HelloController {
    private final HelloJdbcService helloJdbcService;

    public HelloController(HelloJdbcService helloJdbcService) {
        this.helloJdbcService = helloJdbcService;
    }

    @GetMapping("/hello")
    public String hello(String name) {
        SimpleHelloService simpleHelloService = new SimpleHelloService();

        // null 이라면 예외를 던진다.
        return simpleHelloService.sayHello(Objects.requireNonNull(name));
    }

    @GetMapping("/count")
    public String count(String name) {
        return "count: "  + helloJdbcService.countOf(name);
    }
}
