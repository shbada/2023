package tobyspring.helloboot.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController // 이 어노테이션도 메타 어노테이션으로 @Component, @Controller 를 가지고있다.
public class HelloController_v3 implements ApplicationContextAware {
    private final HelloService helloService; // final : 한번 할당 후, 변경되지 않음을 보장
    /*
        setApplicationContext()는 applicationContext가 생성자를 통해서 인스턴스가 다 만들어지고 호출되는 메서드이므로 이를 final로 지정할 수 없다.
     */
    private ApplicationContext applicationContext;

    // HelloService 타입의 빈을 찾는데, SimpleHelloService 가 Application_v5에서 빈으로 등록되어서 찾아진다.
    public HelloController_v3(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello") // GET 메서드 중 URL /hello를 여기로 매핑한다.
    @ResponseBody // view가 아닌 String 문자열 리턴을 위함  (@RestController 에 의해 dispatcherServlet 은 특별한 지정을 안해도 해당 어노테이션이 달려있는 것으로 한다.)
    public String hello(String name) {
//        SimpleHelloService helloService = new SimpleHelloService();

        // null 이라면 예외를 던진다.
        return helloService.sayHello(Objects.requireNonNull(name));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext);
        this.applicationContext = applicationContext;
    }
}
