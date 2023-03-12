package tobyspring.helloboot.step05_springbootapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration // 구성 정보를 가지고있는 클래스임을 알려주자. Spring Container 이 Bean 어노테이션이 붙은 메서드가 있겠구나 라고 알수있다.
@ComponentScan // @Component 가 붙인 클래스를 찾아서, 빈으로 등록한다.
public class HellobootApplication_v11 {
	/**
	 * servletWebServerFactory(), dispatcherServlet() 는 아직까지 있어야한다.
	 * 이게 있어야 스프링부트의 SpringApplication.run() 에서도 이 Bean을 가져가서 사용한다.
	 * 근데 이게 없어도 원래는 됬었는데? 강의를 계속 들어보자.
	 * @return
	 */
	// factory method
	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	/**
	 * run() 으로 메서드 추출
	 * @param args
	 */
	public static void main(String[] args) { // 실행은 된다. main 메서드가 실행된건 맞다.
		/**
		 * 기존 스프링부트의 xxApplication.java 와 동일해졌다.!
		 * = HellobootApplication.java
		 */
//		MySpringApplication.run(HellobootApplication_v11.class, args);

		/** 스프링 부트가 만들어준, 더 뛰어난 클래스 SpringApplication 사용 */
		SpringApplication.run(HellobootApplication_v11.class, args);
	}
}
