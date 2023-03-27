package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HackingSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackingSpringBootApplication.class, args);
	}

	/**
	 * JSON 직렬화를 담당하는 잭슨(Jackson) 라이브러리를 사용하는 방법.
	 * 아래와 같이 빈을 등록한다.
	 * 스프링 프레임워크의 MessageConverter가 자동으로 활성화된다.
	 * MessageConverter는 POJO 객체를 JSON으로 전환하거나 JSON을 POJO 객체로 전환하는 역할을 담당한다.
	 * @return
	 */
	// tag::jackson[]
	@Bean
	Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	// end::jackson[]
}
