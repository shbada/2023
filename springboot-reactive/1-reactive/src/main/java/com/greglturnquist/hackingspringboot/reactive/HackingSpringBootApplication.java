package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// tag::code[]

/**
 * 자동설정(autoconfiguration)과 컴포넌트 탐색(component scanning) 기능을 포함하는 복합 애너테이션이다.
 */
@SpringBootApplication
public class HackingSpringBootApplication {

	public static void main(String[] args) {
		/**
		 * 이 클래스를 애플리케이션의 시작점으로 등록하는 스프링부트 훅(hook)이다.
		 */
		SpringApplication.run(HackingSpringBootApplication.class, args);
	}
}
// end::code[]
