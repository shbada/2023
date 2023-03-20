package tobyspring.helloboot.step14_multiValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerProperties {
    private String contextPath;
    private int port;
}
