package tobyspring.helloboot.step17_DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import tobyspring.helloboot.HelloBootTest;

@HelloBootTest
//@Rollback(false)
public class JdbcTemplateTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXIST hello(name varchar(50) primary key, count int)");
    }

    @Test
    void insertAndQuery() {
        jdbcTemplate.update("insert into hello values(?, ?)", "Seohae", 3);
        jdbcTemplate.update("insert into hello values(?, ?)", "Spring", 1);

        Long count = jdbcTemplate.queryForObject("select count(*) from hello", Long.class);
        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    void insertAndQuery2() {
        jdbcTemplate.update("insert into hello values(?, ?)", "Seohae2", 3);
        jdbcTemplate.update("insert into hello values(?, ?)", "Spring2", 1);

        Long count = jdbcTemplate.queryForObject("select count(*) from hello", Long.class);

        // 위 Test에서 실행되더라도 롤백되므로 2개가 맞다.
        // 상위에 @Rollback(false) 하면 롤백되지 않아서, 4여야 성공한다.
        Assertions.assertThat(count).isEqualTo(2);
    }
}
