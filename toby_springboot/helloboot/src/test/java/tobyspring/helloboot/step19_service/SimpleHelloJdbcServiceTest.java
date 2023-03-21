package tobyspring.helloboot.step19_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tobyspring.helloboot.step18_repository.Hello;
import tobyspring.helloboot.step18_repository.HelloRepository;

import static org.junit.jupiter.api.Assertions.*;

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