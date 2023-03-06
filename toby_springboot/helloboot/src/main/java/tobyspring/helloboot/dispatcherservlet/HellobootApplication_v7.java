package tobyspring.helloboot.dispatcherservlet;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import tobyspring.helloboot.di.SimpleHelloService;
import tobyspring.helloboot.frontcontroller.HelloController;

/*
@SpringBootApplication 를 지워보자.

dispatcherServlet으로 전환
 */
public class HellobootApplication_v7 {

	public static void main(String[] args) { // 실행은 된다. main 메서드가 실행된건 맞다.
		// 도우미 클래스를 사용
		// TomcatServletWebServer 생성을 지원하는 Factory
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

		/** Spring Container */
		// GenericWebApplicationContext : 웹 환경에서 사용하도록 기능 추가
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
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
		applicationContext.registerBean(HelloController.class); // 빈 등록
		applicationContext.registerBean(SimpleHelloService.class); // 빈 등록 (구체클래스 지정)
		applicationContext.refresh(); // [초기화] applicationContext가 bean Obejct를 만들어준다.
	}

}
