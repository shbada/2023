package tobyspring.helloboot.step13_value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import tobyspring.helloboot.step07_4_configannotation.MyAutoConfiguration;

@MyAutoConfiguration
public class PropertyPlaceholderConfig {
    /**
     * @Value("${contextPath}") 사용을 위한 빈 등록
     * @return
     */
    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
