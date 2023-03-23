package tobyspring.helloboot.step16_postProcessor2;

import lombok.Getter;
import lombok.Setter;
import tobyspring.helloboot.step15_postProcessor.MyConfigurationProperties;

@Getter
@Setter
//@Component
@MyConfigurationProperties(prefix = "server")
public class ServerProperties {
    private String contextPath; // server.contextPath
    private int port; // server.port
}
