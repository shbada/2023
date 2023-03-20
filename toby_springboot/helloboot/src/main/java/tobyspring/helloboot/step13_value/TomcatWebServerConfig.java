package tobyspring.helloboot.step13_value;

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
    /**
     * PropertySourcesPlaceholderConfigurer
     * 위 클래스가 빈으로 등록되어야 후처리기로 동작한다.
     */
    @Value("${contextPath}")
    String contextPath;

    @Bean("tomcatWebServerFactory")
    public ServletWebServerFactory servletWebServerFactory(Environment env) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.setContextPath("/app"); // 8080 with context path /app
        // application.properties
        factory.setContextPath(env.getProperty("contextPath")); // 8080 with context path /app
        factory.setContextPath(contextPath); // 8080 with context path /app

        return factory;
    }
}
