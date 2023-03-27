package com.greglturnquist.hackingspringboot.reactive.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. R소켓 : 자바로 구현된 R소켓 프로토콜
 * 2. 리액터 네티 : 네티는 리액티브 메시지 관리자 역할도 충분히 수행할 수 있다. 리액터로 감싸져서 더 강력한 서버로 만들어졌다.
 * 3. 스프링 + 잭슨(Jackson) : 메시지가 선택되고 직렬화되며 전송되고 역직렬화되고 라우팅되는 것은 프로토콜의 리액티브 속성만큼이나 중요하다.
 * 스프링의 입증된 메시지 처리 아키텍처와 잭슨을 함께 사용하자.
 */
@SpringBootApplication
public class HackingSpringBootRSocketServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackingSpringBootRSocketServerApplication.class, args);
	}
}
