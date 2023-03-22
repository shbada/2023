package tobyspring.helloboot.step20_springboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.helloboot.step18_repository.HelloRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional // 롤백을 위함
class HelloRepositoryJdbcTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    HelloRepository helloRepository;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXIST hello(name varchar(50) primary key, count int)");
    }

    @Test
    void findHelloFailed() {
        Assertions.assertThat(helloRepository.findHello("Seohae")).isNull();
    }

    @Test
    void increaseCount() {
        Assertions.assertThat(helloRepository.countOf("Seohae")).isEqualTo(0);

        helloRepository.increaseCount("Seohae");
        Assertions.assertThat(helloRepository.countOf("Seohae")).isEqualTo(1);

        helloRepository.increaseCount("Seohae");
        Assertions.assertThat(helloRepository.countOf("Seohae")).isEqualTo(2);
    }
}