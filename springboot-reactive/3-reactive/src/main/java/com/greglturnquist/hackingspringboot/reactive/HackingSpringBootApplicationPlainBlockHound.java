package com.greglturnquist.hackingspringboot.reactive;

import reactor.blockhound.BlockHound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HackingSpringBootApplicationPlainBlockHound {

	// tag::blockhound[]
	public static void main(String[] args) {
		// run()보다 먼저 작성한다.
		BlockHound.install(); // 블로킹 메서드를 검출하고, 해당 스레드가 블로킹 메서드 호출을 허용하는지 검사할 수 있다.

		SpringApplication.run(HackingSpringBootApplicationPlainBlockHound.class, args);
	}
	// end::blockhound[]
}
