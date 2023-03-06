package tobyspring.helloboot.component;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import tobyspring.helloboot.dispatcherservlet.HelloController;
import tobyspring.helloboot.dispatcherservlet.HelloService;
import tobyspring.helloboot.dispatcherservlet.HellobootApplication_v7;
import tobyspring.helloboot.dispatcherservlet.SimpleHelloService;

/*
@SpringBootApplication 를 지워보자.

@Component 사용
 */
@Configuration // 구성 정보를 가지고있는 클래스임을 알려주자. Spring Container 이 Bean 어노테이션이 붙은 메서드가 있겠구나 라고 알수있다.
@ComponentScan // @Component 가 붙인 클래스를 찾아서, 빈으로 등록한다.
public class HellobootApplication_v9 {
	// @Component 를 사용했으므로 @Bean 메서드 없이 빈 등록 가능
	// factory method
//	@Bean // Bean 임을 알려주자.
//	public tobyspring.helloboot.dispatcherservlet.HelloController helloController(tobyspring.helloboot.dispatcherservlet.HelloService helloService) { // spring Container 에서 넣어주게 한다.
//		return new HelloController(helloService);
//	}
//
//	@Bean
//	public HelloService helloService() {
//		return new SimpleHelloService();
//	}

	public static void main(String[] args) { // 실행은 된다. main 메서드가 실행된건 맞다.
		// 도우미 클래스를 사용
		// TomcatServletWebServer 생성을 지원하는 Factory
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

		/** Spring Container */
		// AnnotationConfigWebApplicationContext : java 코드로 만든 configuration 정보를 가져오도록 한다.
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh(); // 생략하면 안된다.

				// 람다식
				WebServer webServer = serverFactory.getWebServer(servletContext -> {
//			HelloController helloController = new HelloController();

					// 매핑 정보를 알려주지 않았으니 이렇게만 설정하면 404 오류가 난다.
					servletContext.addServlet("dispatcherServlet",
							new DispatcherServlet(this) // 자기 자신을 참조 (applicationContext)
					).addMapping("/*"); // request url
				});
				webServer.start(); // tomcat servlet container 가 동작한다.

			}
		};
		// 여기서 출발해서 bean object를 만들어줘.
		applicationContext.register(HellobootApplication_v7.class);
		applicationContext.refresh(); // [초기화] applicationContext가 bean Obejct를 만들어준다.
	}

}
