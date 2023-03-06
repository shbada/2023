package tobyspring.helloboot.component;

import org.springframework.stereotype.Component;

@Component
public interface HelloService {
    public String sayHello(String name);
}
