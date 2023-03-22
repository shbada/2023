package tobyspring.helloboot.step20_springboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import tobyspring.helloboot.step18_repository.Hello;
import tobyspring.helloboot.step18_repository.HelloRepository;
import tobyspring.helloboot.step19_service.SimpleHelloJdbcService;

@JdbcTest
class SimpleHelloJdbcServiceTest {

    @Test
    void sayHello() {
        SimpleHelloJdbcService helloService = new SimpleHelloJdbcService(new HelloRepository() {
            @Override
            public Hello findHello(String name) {
                return null;
            }

            @Override
            public void increaseCount(String name) {

            }
        });

        String ret = helloService.sayHello("Test");
        Assertions.assertThat(ret).isEqualTo("Hello Test");
    }
}