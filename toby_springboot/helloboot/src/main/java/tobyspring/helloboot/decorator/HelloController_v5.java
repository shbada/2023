package tobyspring.helloboot.decorator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tobyspring.helloboot.di.HelloService;

@RestController
public class HelloController_v5 {
    private final HelloService helloService;

    public HelloController_v5(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello(String name) {
        // null 체크 개선
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException();
        }

        return helloService.sayHello(name);
    }
}
