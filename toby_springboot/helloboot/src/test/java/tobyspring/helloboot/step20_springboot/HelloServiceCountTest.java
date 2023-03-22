package tobyspring.helloboot.step20_springboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.helloboot.step18_repository.HelloRepository;
import tobyspring.helloboot.step19_service.HelloJdbcService;

import java.util.stream.IntStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) // rollback 지정하지않음
@Transactional // 롤백을 위함
//@HelloBootTest
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
