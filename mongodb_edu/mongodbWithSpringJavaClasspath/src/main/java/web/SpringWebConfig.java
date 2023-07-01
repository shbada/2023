package web;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
//@ComponentScan({"com.project.web"})
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MongoClient mongoClients(){

        ConnectionString connectionString = new ConnectionString(
                // Connect to ReplicaSet
                // "mongodb://admin:admin@localhost:27017,localhost:27018,localhost:27019/test?authSource=admin&replicaSet=replset"
                // Connect to StandAlone
                //" mongodb://admin:admin@localhost:27017/test?authSource=Admin"
                // Connect to Localhost
                "mongodb://localhost:27017"
        );

        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder()
                        .applyConnectionString(connectionString)
                        .build();

        return MongoClients.create(mongoClientSettings);
    }

}
