package tobyspring.helloboot.step19_service;

import org.springframework.stereotype.Component;

@Component
public interface HelloJdbcService {
    public String sayHello(String name);

    int countOf(String name);
}
