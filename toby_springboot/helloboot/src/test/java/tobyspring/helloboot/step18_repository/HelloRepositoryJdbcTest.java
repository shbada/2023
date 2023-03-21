package tobyspring.helloboot.step18_repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import tobyspring.helloboot.HelloBootTest;
import tobyspring.helloboot.step17_DataSource.JdbcTemplateTest;

import static org.junit.jupiter.api.Assertions.*;

@HelloBootTest
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