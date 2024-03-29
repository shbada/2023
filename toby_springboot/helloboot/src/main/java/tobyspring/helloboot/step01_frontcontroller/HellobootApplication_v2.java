package tobyspring.helloboot.step01_frontcontroller;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
@SpringBootApplication 를 지워보자.

FrontController
 */
public class HellobootApplication_v2 {

	public static void main(String[] args) { // 실행은 된다. main 메서드가 실행된건 맞다.
//		new Tomcat().start(); // tomcat 서버를 띄울수있나? 아니다. 좀더 복잡하다.

		// 도우미 클래스를 사용
		// TomcatServletWebServer 생성을 지원하는 Factory
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

		// 람다식
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("frontcontroller", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 인증, 보안, 다국어, 공통 기능
					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						String name = req.getParameter("name");

						resp.setStatus(200);
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println("Hello Servlet " + name);
					} else if (req.getRequestURI().equals("/user")) { /** 추가 */
						//
					} else {
						resp.setStatus(HttpStatus.NOT_FOUND.value()); /** 추가 */
					}
				}
			}).addMapping("/*"); // request url
		});
		webServer.start(); // tomcat servlet container 가 동작한다.

		System.out.println("Hello Containerless Standalone Application");
	}

}
