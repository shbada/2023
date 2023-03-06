package tobyspring.helloboot.frontcontroller;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
@SpringBootApplication 를 지워보자.

ServletContainer  (=Tomcat)
- Tomcat 으로 main 메서드를 수행시켜보자.
- Tomcat도 Java로 만들어진 프로그램이다.
- 그러므로 Tomcat을 내장하여 사용해도 좋다. (Embedded Tomcat) : 이미 라이브러리에 들어와있다.
 */
public class HellobootApplication_v1 {

	public static void main(String[] args) { // 실행은 된다. main 메서드가 실행된건 맞다.
//		new Tomcat().start(); // tomcat 서버를 띄울수있나? 아니다. 좀더 복잡하다.

		// 도우미 클래스를 사용
		// TomcatServletWebServer 생성을 지원하는 Factory
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//		WebServer webServer = serverFactory.getWebServer(); // ServletContainer 생성 함수
		// 파라미터로 ServletContextInitializer 를 넘긴다.
		// ServletContext 구성을 위해 사용되어지는 인터페이스
		// = Servlet Container 에다가 Servlet 을 등록하는데 사용되어지는 인터페이스
//		WebServer webServer = serverFactory.getWebServer(new ServletContextInitializer() {
//			@Override
//			public void onStartup(ServletContext servletContext) throws ServletException {
//
//			}
//		});

		// 람다식
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("hello", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// querystring name parameter
					String name = req.getParameter("name");

					resp.setStatus(200);
					resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
					resp.getWriter().println("Hello Servlet " + name);
				}
			}).addMapping("/hello"); // request url
		});
		webServer.start(); // tomcat servlet container 가 동작한다.

		System.out.println("Hello Containerless Standalone Application");
	}

}
