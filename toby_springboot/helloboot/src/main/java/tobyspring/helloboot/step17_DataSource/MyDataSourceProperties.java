package tobyspring.helloboot.step17_DataSource;

import lombok.Getter;
import lombok.Setter;
import tobyspring.helloboot.step15_postProcessor.MyConfigurationProperties;

@MyConfigurationProperties(prefix = "data")
@Getter
@Setter
public class MyDataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
