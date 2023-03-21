package tobyspring.helloboot.step19_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tobyspring.helloboot.HelloBootTest;
import tobyspring.helloboot.step02_di.HelloService;
import tobyspring.helloboot.step18_repository.HelloRepository;

import java.util.stream.IntStream;

@HelloBootTest
public class HelloServiceCountTest {
    @Autowired
    HelloJdbcService helloJdbcService;

    @Autowired
    HelloRepository helloRepository;

    @Test
    void sayHelloIncreaseCount() {
        IntStream.rangeClosed(1, 10).forEach(count -> {
            helloJdbcService.sayHello("Seohae");
            Assertions.assertThat(helloRepository.countOf("Seohae")).isEqualTo(count);
        });
    }
}
