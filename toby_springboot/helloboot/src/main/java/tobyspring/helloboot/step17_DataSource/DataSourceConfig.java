package tobyspring.helloboot.step17_DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tobyspring.helloboot.step07_4_configannotation.MyAutoConfiguration;
import tobyspring.helloboot.step09_2_classutils.ConditionalMyOnClass;
import tobyspring.helloboot.step16_postProcessor2.EnableMyConfigurationProperties;

import javax.sql.DataSource;
import java.sql.Driver;

@MyAutoConfiguration
@ConditionalMyOnClass("org.springframework.jdbc.core.JdbcOperations")
@EnableMyConfigurationProperties(MyDataSourceProperties.class)
@EnableTransactionManagement /* 아래 정의해놓은 jdbcTransactionManager 과 함께 @Transactional 어노테이션을 사용 가능하다. */
public class DataSourceConfig {
    @Bean
    @ConditionalMyOnClass("com.zaxxer.hikari.HikariDataSource")
    @ConditionalOnMissingBean // 아래 빈 등록이 안될경우
    DataSource hikariDataSource(MyDataSourceProperties sourceProperties) {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(sourceProperties.getDriverClassName());
        dataSource.setJdbcUrl(sourceProperties.getUrl());
        dataSource.setUsername(sourceProperties.getUsername());
        dataSource.setPassword(sourceProperties.getPassword());

        return dataSource;
    }

    @Bean
    @ConditionalOnMissingBean // 위 빈 등록이 안될경우
    DataSource dataSource(MyDataSourceProperties sourceProperties) throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName(sourceProperties.getDriverClassName()));
        dataSource.setUrl(sourceProperties.getUrl());
        dataSource.setUsername(sourceProperties.getUsername());
        dataSource.setPassword(sourceProperties.getPassword());

        return dataSource;
    }

    @Bean
    @ConditionalOnSingleCandidate(DataSource.class)
    @ConditionalOnMissingBean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /*
        직접 Access 하여 트랜잭션 처리 가능하고, 선언적 트랜잭션으로 사용가능
        직접 Access 사용하면 PlatformTransactionManager 인터페이스를 주입받아서 사용하게된다.
     */
    @Bean
    @ConditionalOnSingleCandidate(DataSource.class)
    @ConditionalOnMissingBean
    JdbcTransactionManager jdbcTransactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }
}
