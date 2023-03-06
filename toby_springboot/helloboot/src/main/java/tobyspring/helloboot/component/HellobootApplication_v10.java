package tobyspring.helloboot.component;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import tobyspring.helloboot.dispatcherservlet.HellobootApplication_v7;

/*
@SpringBootApplication 를 지워보자.

ServletWebServerFactory, DispatcherServlet 도 Bean 으로 등록하여 사용
 */
@Configuration // 구성 정보를 가지고있는 클래스임을 알려주자. Spring Container 이 Bean 어노테이션이 붙은 메서드가 있겠구나 라고 알수있다.
@ComponentScan // @Component 가 붙인 클래스를 찾아서, 빈으로 등록한다.
public class HellobootApplication_v10 {
	// factory method
	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

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

				ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
				DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
				/*
					주석해봐도 잘 될까?
					잘 된다.
					이건 주입하지 않아도 Spring Container 가 dispatcherServlet 에 주입해준다.
					DispatcherServlet > ApplicationContextAware > setApplicationContext 메서드가 있다.
					-> ApplicationContextAware : 빈을 컨테이너가 등록하고 관리하는 중에, 컨테이너가 관리하는 오브젝트를 빈에다가 주입해주는 라이프사이클 인터페이스
					-> ApplicationContextAware을 구현한 클래스는 Spring Container 가 인터페이스의 setXXX()를 이용해서 주입해줄 수 있다.
				 */
//				dispatcherServlet.setApplicationContext(this);

				// 람다식
				WebServer webServer = serverFactory.getWebServer(servletContext -> {
//			HelloController helloController = new HelloController();

					// 매핑 정보를 알려주지 않았으니 이렇게만 설정하면 404 오류가 난다.
					servletContext.addServlet("dispatcherServlet", dispatcherServlet)
							.addMapping("/*"); // request url
				});
				webServer.start(); // tomcat servlet container 가 동작한다.

			}
		};
		// 여기서 출발해서 bean object를 만들어줘.
		applicationContext.register(HellobootApplication_v7.class);
		applicationContext.refresh(); // [초기화] applicationContext가 bean Obejct를 만들어준다.
	}

}
