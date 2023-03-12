package tobyspring.helloboot.step04_component;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController // 이 어노테이션도 메타 어노테이션으로 @Component, @Controller 를 가지고있다.
public class HelloController_v2 {
    private final HelloService helloService; // final : 한번 할당 후, 변경되지 않음을 보장

    // HelloService 타입의 빈을 찾는데, SimpleHelloService 가 Application_v5에서 빈으로 등록되어서 찾아진다.
    public HelloController_v2(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello") // GET 메서드 중 URL /hello를 여기로 매핑한다.
    @ResponseBody // view가 아닌 String 문자열 리턴을 위함  (@RestController 에 의해 dispatcherServlet 은 특별한 지정을 안해도 해당 어노테이션이 달려있는 것으로 한다.)
    public String hello(String name) {
//        SimpleHelloService helloService = new SimpleHelloService();

        // null 이라면 예외를 던진다.
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
