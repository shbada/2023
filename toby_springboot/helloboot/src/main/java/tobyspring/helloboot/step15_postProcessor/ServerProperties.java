package tobyspring.helloboot.step15_postProcessor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
//@Component
@MyConfigurationProperties(prefix = "server")
public class ServerProperties {
    private String contextPath; // server.contextPath
    private int port; // server.port
}
