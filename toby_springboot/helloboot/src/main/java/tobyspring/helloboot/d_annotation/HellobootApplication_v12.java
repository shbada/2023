package tobyspring.helloboot.d_annotation;

import org.springframework.boot.SpringApplication;
import tobyspring.helloboot.config.MySpringBootApplication;

/**
 * toby_springboot/helloboot/src/main/java/tobyspring/helloboot/config
 * 패키지로 Config.java를 옮기면 해당 패키지의 scan 대상이 아니라서 Config 안의 빈 등록이 안된다.
 * -> @MySpringBootApplication 안에 @Import 를 추가하자.
 */
@MySpringBootApplication
public class HellobootApplication_v12 {
	public static void main(String[] args) { // 실행은 된다. main 메서드가 실행된건 맞다.
		/** 스프링 부트가 만들어준, 더 뛰어난 클래스 SpringApplication 사용 */
		SpringApplication.run(HellobootApplication_v12.class, args);
	}
}
