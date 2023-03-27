/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greglturnquist.hackingspringboot.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Service // <1>
public class SpringAmqpItemService {

	private static final Logger log = //
			LoggerFactory.getLogger(SpringAmqpItemService.class);

	private final ItemRepository repository; // <2>

	public SpringAmqpItemService(ItemRepository repository) {
		this.repository = repository;
	}
	// end::code[]

	// tag::listener[]
	@RabbitListener( // <1> 메시지 리스너로 등록되어 메시지를 소비할 수 있다.
			ackMode = "MANUAL", //
			bindings = @QueueBinding( // <2> 큐를 익스체인지에 바인딩하는 방법 지정
					value = @Queue, // <3> 임의의 지속성 없는 큐를 생성. 특정 큐를 바인딩하려면 @Queue의 인자로 큐의 이름을 지정
					exchange = @Exchange("hacking-spring-boot"), // <4> 큐와 연결될 익스체인지를 지정한다.
					key = "new-items-spring-amqp")) // <5> 라우팅 키를 지정한다.
	public Mono<Void> processNewItemsViaSpringAmqp(Item item) { // <6> @RabbitListener 에서 지정한 내용에 맞는 메시지가 들어오면 수행된다.
		log.debug("Consuming => " + item);
		// then()을 호출해서 저장이 완료될때까지 기다린다.
		return this.repository.save(item).then(); // <7> 몽고DB에 저장된다.
	}
	// end::listener[]
}
