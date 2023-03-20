package tobyspring.helloboot.step14_multiValue;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tobyspring.helloboot.step07_4_configannotation.MyAutoConfiguration;
import tobyspring.helloboot.step09_2_classutils.ConditionalMyOnClass;

import java.util.Objects;

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
public class ServerPropertiesConfig {
//    @Bean
//    public ServerProperties serverProperties(Environment environment) {
//        ServerProperties properties = new ServerProperties();
//
//        properties.setContextPath(environment.getProperty("contextPath"));
//        properties.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("port"))));
//
//        return properties;
//    }

    /**
     * springBoot가 제공해주는 Binder
     * @param environment
     * @return
     */
    @Bean
    public ServerProperties serverProperties(Environment environment) {
        // 자동으로 key 값을 찾아서 매핑해준다.
        return Binder.get(environment).bind("", ServerProperties.class).get();
    }
}

