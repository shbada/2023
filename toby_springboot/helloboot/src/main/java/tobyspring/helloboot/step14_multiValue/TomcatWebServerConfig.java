package tobyspring.helloboot.step14_multiValue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tobyspring.helloboot.step07_4_configannotation.MyAutoConfiguration;
import tobyspring.helloboot.step09_2_classutils.ConditionalMyOnClass;

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
public class TomcatWebServerConfig {
//    @Value("${contextPath}")
//    String contextPath;
//
//    // 존재하지 않으면? Could not resolve placeholder ... error 발생
//    // default 8080 셋팅
//    @Value("${port:8080}")
//    int port;

    @Bean("tomcatWebServerFactory")
    public ServletWebServerFactory servletWebServerFactory(ServerProperties properties) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.setContextPath(properties.getContextPath()); // 8080 with context path /app
        factory.setPort(properties.getPort()); // 8080 with context path /app

        return factory;
    }
}
