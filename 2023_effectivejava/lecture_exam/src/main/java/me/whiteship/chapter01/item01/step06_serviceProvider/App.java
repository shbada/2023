package me.whiteship.chapter01.item01.step06_serviceProvider;

import me.whiteship.chapter01.item01.step03.HelloService;
import me.whiteship.chapter01.item01.step06_serviceProvider.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    /**
     * 서비스 접근 API
     * 서비스를 가져온다.
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloService helloService = applicationContext.getBean(HelloService.class);
        System.out.println(helloService.hello());
    }
}
