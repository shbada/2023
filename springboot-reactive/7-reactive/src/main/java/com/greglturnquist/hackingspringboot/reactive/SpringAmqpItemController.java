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

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@RestController // <1>
public class SpringAmqpItemController {

	private static final Logger log = //
			LoggerFactory.getLogger(SpringAmqpItemController.class);

	private final AmqpTemplate template; // <2>

	public SpringAmqpItemController(AmqpTemplate template) {
		this.template = template;
	}
	// end::code[]

	/**
	 * 리액터를 사용할때는 여러 단계의 작업 절차를 만들게 된다.
	 * 리액터는 스케줄러를 통해 개별 수행 단계가 어느 스레드에서 실행될지 지정할 수 있다.
	 * 한개의 스레드만을 사용하면서도 비동기 논블로킹 코드를 작성할 수 있다.
	 * 한개의 스레드가 작업을 수행할 수 있을때, 다시말하면 스레드가 시스템 자원의 가용성에 반응할 준비가 돼있을때
	 * 개별 수행 단계를 실행하는 방식을 사용하면 가능하다.
	 * 하나의 작업단계가 완료되면 스레드는 리액터의 작업 코데네이터에게 반환되고, 다음에 어떤 작업 단계를 실행할지 결정된다.
	 * 모든 작업이 이처럼 개별 단계가 완료될때마다 스케줄러에게 스레드를 반환하는 패러다임으로 수행될 수 있다면,
	 * 스레드의 숫자는 전통적인 멀티스레드 프로그래밍에서만큼 중요하지는 않게된다.
	 *
	 * 작업 수행 단계 중에 블로킹 API 호출이 포함된다면 리액터에게 알려서 블로킹 API를 별도의 스레드에서 호출하게 해야 의도하지않은 스레드 낭비를 방지할 수 있다.
	 * 리액터는 다음과 같이 여러 방법으로 스레드를 사용할 수 있다.
	 *
	 * Schedulers.immediate() : 현재 스레드
	 * Schedulers.single() : 재사용 가능한 하나의 스레드. 현재 수행중인 리액터 플로우뿐만 아니라 호출되는 모든 작업이 동일한 하나의 스레드에서 실행된다.
	 * Schedulers.newSingle() : 새로 생성한 전용 스레드
	 * Schedulers.boundedElastic() : 작업량에 따라 스레드 숫자가 늘어나거나 줄어드는 신축성있는 스레드풀
	 * Schedulers.parallel() : 병렬 작업에 적합하도록 최적화된 고정 크기 워커 스레드 풀
	 * Schedulers.fromExecutorService() : ExecuterService 인스턴스를 감싸서 재사용
	 *
	 * 리액터 플로우에서 스케줄러를 변경하는 방법은 두가지다.
	 * 1) publishOn() : 호출되는 시점 이후로는 지정한 스케줄러를 사용한다. 사용하는 스케줄러를 여러번 바꿀 수도 있다.
	 * 2) subscribeOn() : 플로우 전 단계에 걸쳐 사용되는 스케줄러를 지정한다. 플로우 전체에 영향을 미치므로 영향 범위가 publishOn()보다 더 넓다.
	 *
	 * @param item
	 * @return
	 */
	// tag::post[]
	@PostMapping("/items") // <1>
	Mono<ResponseEntity<?>> addNewItemUsingSpringAmqp(@RequestBody Mono<Item> item) { // <2>
		return item //
				/*
				    AmqpTemplate은 블로킹 API를 호출한다.
				    subscribeOn()을 통해 바운디드 엘라스틱 스케줄러(bounded elastic shceduler)에서 관리하는 별도의 스레드에서 실행되게한다.
				    이 신축성 스레드 풀은 별도의 스레드 풀이므로, 블로킹 API 호출이 있더라도 다른 리액터 플로우에 블로킹 영향을 전파하지 않는다.
				 */
				.subscribeOn(Schedulers.boundedElastic())// <3>
				.flatMap(content -> { //
					return Mono //
							.fromCallable(() -> { // <4> Callable로 감싸고, Mono.fromCallable()을 통해 Mono를 생성한다.
								this.template.convertAndSend( // <5>
										"hacking-spring-boot", "new-items-spring-amqp", content);
								return ResponseEntity.created(URI.create("/items")).build(); // <6>
							});
				});
	}
	// end::post[]
}
