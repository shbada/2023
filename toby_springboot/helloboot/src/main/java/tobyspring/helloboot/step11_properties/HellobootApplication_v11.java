package tobyspring.helloboot.step11_properties;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class HellobootApplication_v11 {
	@Bean
	ApplicationRunner applicationRunner(Environment env) {
		return args -> {
			// application.properties
			String name = env.getProperty("my.name");
			System.out.println("my.name : " + name);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(HellobootApplication_v11.class, args);
	}

}
