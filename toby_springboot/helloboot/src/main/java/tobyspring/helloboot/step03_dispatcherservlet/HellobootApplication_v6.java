package tobyspring.helloboot.step03_dispatcherservlet;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import tobyspring.helloboot.step02_di.SimpleHelloService;
import tobyspring.helloboot.step01_frontcontroller.HelloController;

/*
@SpringBootApplication 를 지워보자.

dispatcherServlet으로 전환
 */
public class HellobootApplication_v6 {

	public static void main(String[] args) { // 실행은 된다. main 메서드가 실행된건 맞다.
		// 도우미 클래스를 사용
		// TomcatServletWebServer 생성을 지원하는 Factory
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

		/** Spring Container */
		// GenericWebApplicationContext : 웹 환경에서 사용하도록 기능 추가
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
		applicationContext.registerBean(HelloController.class); // 빈 등록
		applicationContext.registerBean(SimpleHelloService.class); // 빈 등록 (구체클래스 지정)
		applicationContext.refresh(); // [초기화] applicationContext가 bean Obejct를 만들어준다.

		// 람다식
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
//			HelloController helloController = new HelloController();

			// 매핑 정보를 알려주지 않았으니 이렇게만 설정하면 404 오류가 난다. (@RequestMapping를 넣어야 찾을수있음)
			servletContext.addServlet("dispatcherServlet",
					new DispatcherServlet(applicationContext)
			).addMapping("/*"); // request url
		});
		webServer.start(); // tomcat servlet container 가 동작한다.
	}

}
