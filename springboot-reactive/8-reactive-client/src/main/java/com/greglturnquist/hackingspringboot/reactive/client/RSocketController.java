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

package com.greglturnquist.hackingspringboot.reactive.client;

import static io.rsocket.metadata.WellKnownMimeType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.*;

import java.net.URI;
import java.time.Duration;

import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@RestController // <1>
public class RSocketController {

	private final Mono<RSocketRequester> requester; // <2> Mono를 사용하므로 R소켓에 연결된 코드는 새 클라이언트가 구독할때마다 호출된다.

	// 스프링 부트는 RsocketRequesterAutoConfiguration 정책 안에서 자동설정으로 RSocketRequester.Builder builder 빈을 만들어준다.
	// Jackson을 포함해서 여러가지 인코더/디코더를 사용할 수 있으며, 컨트롤러의 생성자를 통해 의존관계를 주입할 수 있다.
	/*
	[RSocketRequester]
	- R소켓에 무언가를 보낼때 사용
	- R소켓의 API는 프로젝트 리액터를 사용
	- RSocketRequester를 사용해야 스프링 프레임워크와 연동된다. 이렇게하면 도착지를 기준으로 메시지를 라우팅할 수 있다.

	- Mono로 감싸는 이유
	   - 리액터의 Mono 패러다임은 연결을 R소켓 연결 세부정보를 포함하는 지연 구조체(lazy construct)로 전환한다.
	     아무도 연결하지 않으면 R소켓은 열리지 않고, 누군가 구독을 해야 세부정보가 여러 구독자에 공유될 수 있다.
	     하나의 R소켓만으로 모든 구독자에게 서비스할 수 있다.
	     R소켓을 구독자마다 1개씩 만들 필요는 없으며, 대신 하나의 R소켓 파이프에 대해 구독자별로 하나씩 연결을 생성한다.
	 */
	public RSocketController(RSocketRequester.Builder builder) { // <3>
		this.requester = builder //
				.dataMimeType(APPLICATION_JSON) // <4>
				.metadataMimeType(parseMediaType(MESSAGE_RSOCKET_ROUTING.toString())) // <5>
				.connectTcp("localhost", 7000) // <6> R소켓 서버 연결
				.retry(5) // <7> 5번까지 재시도 가능
				.cache(); // <8> hot source로 전환 (가장 최근의 신호는 캐시돼있을 수 있으며, 구독자는 사본을 가지고있을 수도 있다. 다수의 클라이언트가 동일한 하나의 데이터를 요구할때 효율성을 높일 수 있다.)
	}
	// end::code[]

	// tag::request-response[]
	@PostMapping("/items/request-response") // <1>
	Mono<ResponseEntity<?>> addNewItemUsingRSocketRequestResponse(@RequestBody Item item) {
		return this.requester //
				.flatMap(rSocketRequester -> rSocketRequester //
						.route("newItems.request-response") // <2>
						.data(item) // <3>
						.retrieveMono(Item.class)) // <4>
				.map(savedItem -> ResponseEntity.created( // <5>
						URI.create("/items/request-response")).body(savedItem));
	}
	// end::request-response[]

	/**
	 * R소켓 요청 - 스트림 방식
	 * @return
	 */
	@GetMapping(value = "/items/request-stream", produces = MediaType.APPLICATION_NDJSON_VALUE) // <1>
	Flux<Item> findItemsUsingRSocketRequestStream() {
		return this.requester //
				.flatMapMany(rSocketRequester -> rSocketRequester // <2> Flux 반환
						.route("newItems.request-stream") // <3> 라우팅
						.retrieveFlux(Item.class) // <4>
						.delayElements(Duration.ofSeconds(1))); // <5> 1초에 1건씩 반환하도록 한다.
	}

	/**
	 * R소켓 실행 후 망각으로 전환
	 * @param item
	 * @return
	 */
	// tag::fire-and-forget[]
	@PostMapping("/items/fire-and-forget")
	Mono<ResponseEntity<?>> addNewItemUsingRSocketFireAndForget(@RequestBody Item item) {
		return this.requester //
				.flatMap(rSocketRequester -> rSocketRequester //
						.route("newItems.fire-and-forget") // <1>
						.data(item) //
						.send()) // <2>
				/*
				Mono<Void>를 받았다.
				따라서 map()을 해도 아무일도 일어나지 않는다.
				그래서 HTTP 201 Created를 반환하기 위해 map()이 아닌 then()과 Mono.just()로 Mono를 새로 만들어서 반환하자.
				 */
				.then( // <3>
						Mono.just( //
								ResponseEntity.created( //
										URI.create("/items/fire-and-forget")).build()));
	}
	// end::fire-and-forget[]

	/**
	 * 웹플럭스 요청을 R소켓으로 전환
	 *  produces = TEXT_EVENT_STREAM_VALUE
	 *  : 응답할 결과가 생길때마다 결괏값을 스트림에 흘려보낸다는 것을 의미한다.
	 *
	 * 결괏값이 생길때마다 결과를 화면에 표시하고 실행을 종료하지 않고 추가로 응답을 받을 수 있는 대기 상태로 남는다.
	 * @return
	 */
	// tag::request-stream[]
	@GetMapping(value = "/items", produces = TEXT_EVENT_STREAM_VALUE) // <1>
	Flux<Item> liveUpdates() {
		return this.requester //
				.flatMapMany(rSocketRequester -> rSocketRequester //
						.route("newItems.monitor") // <2>
						.retrieveFlux(Item.class)); // <3>
	}
	// end::request-stream[]
}
