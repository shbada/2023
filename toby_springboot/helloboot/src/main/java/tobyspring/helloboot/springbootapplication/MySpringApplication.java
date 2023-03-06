package tobyspring.helloboot.springbootapplication;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {
    public static void run(Class<?> applicationClass, String... args) {
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

        /** Spring Container */
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh(); // 생략하면 안된다.

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);

                // 람다식
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    // 매핑 정보를 알려주지 않았으니 이렇게만 설정하면 404 오류가 난다.
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*"); // request url
                });
                webServer.start(); // tomcat servlet container 가 동작한다.

            }
        };
        // 여기서 출발해서 bean object를 만들어줘.
        applicationContext.register(applicationClass);
        applicationContext.refresh(); // [초기화] applicationContext가 bean Obejct를 만들어준다.
    }
}
