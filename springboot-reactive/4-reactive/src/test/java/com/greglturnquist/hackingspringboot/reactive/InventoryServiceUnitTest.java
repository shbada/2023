/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greglturnquist.hackingspringboot.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.greglturnquist.hackingspringboot.reactive.Cart;
import com.greglturnquist.hackingspringboot.reactive.CartItem;
import com.greglturnquist.hackingspringboot.reactive.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.InventoryService;
import com.greglturnquist.hackingspringboot.reactive.Item;
import com.greglturnquist.hackingspringboot.reactive.ItemRepository;

/**
 * 서비스 계층 단위 테스트
 * @author Greg Turnquist
 */
// tag::extend[]
@ExtendWith(SpringExtension.class) // <1> 스프링에 특화된 테스트 기능을 사용할 수 있다.
class InventoryServiceUnitTest { // <2>
	// end::extend[]

	// tag::class-under-test[]
	InventoryService inventoryService; // <1> CUT (class under test)

	@MockBean private ItemRepository itemRepository; // <2> 가짜 객체를 스프링 빈으로 등록한다.

	@MockBean private CartRepository cartRepository; // <2>
	// end::class-under-test[]

	// tag::before[]
	@BeforeEach // <1>
	void setUp() {
		// Define test data <2>
		Item sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);
		CartItem sampleCartItem = new CartItem(sampleItem);
		Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

		// Define mock interactions provided
		// by your collaborators <3>
		when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
		when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
		when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

		inventoryService = new InventoryService(itemRepository, cartRepository); // <4>
	}
	// end::before[]

	/**
	 * 리액티브 코드를 테스트 할대는 리액티브 스트림 시그널도 함께 검사해야한다.
	 * 리액티브 스트림은 onSubscribe, onNext, onError, onComplete를 말한다.
	 * 아래는 onNext, onComplete 시그널을 모두 검사하는 테스트 코드다.
	 *
	 * 구독하기 전까지는 아무 일도 일어나지 않는다고 했다.
	 * StepVerifier가 구독을 해준다.
	 * 결괏값을 얻기위해 블로킹 방식으로 기다리는 대신에, 리액터의 테스트 도구가 대신 구독을 하고 값을 확인할 수 있게 해준다.
	 * 값을 검증할 수 있는 적절한 함수를 expectNextMatches(..)에 람다식 인자로 전달해주고,
	 * verifyComplete()를 호출해서 onComplete 시그널을 확인하면 의도한대로 테스트가 동작했음이 보장된다.
	 */
	// tag::test[]
	@Test
	void addItemToEmptyCartShouldProduceOneCartItem() { // <1>
		inventoryService.addItemToCart("My Cart", "item1") // <2>
				.as(StepVerifier::create) // <3> StepVerifier.create()에 메서드 레퍼런스로 연결해서, 테스트 기능을 전담하는 리액터 타입 핸들러 생성
				.expectNextMatches(cart -> { // <4>
					assertThat(cart.getCartItems()).extracting(CartItem::getQuantity) //
							.containsExactlyInAnyOrder(1); // <5>

					assertThat(cart.getCartItems()).extracting(CartItem::getItem) //
							.containsExactly(new Item("item1", "TV tray", "Alf TV tray", 19.99)); // <6>

					return true; // <7> expectNextMatches 메서드는 true 를 반환해야 하므로 이 지점까지 통과했다면 true 반환
				}) //
				.verifyComplete(); // <8> 리액티브 스트림의 complete 시그널이 발생하고 리액터 플로우가 성공적으로 완료됐음을 검증
	}
	// end::test[]

	// tag::test2[]
	@Test
	void alternativeWayToTest() { // <1>
		StepVerifier.create( // 메서드 인자로 전달
				inventoryService.addItemToCart("My Cart", "item1")) //
				.expectNextMatches(cart -> { // <4>
					assertThat(cart.getCartItems()).extracting(CartItem::getQuantity) //
							.containsExactlyInAnyOrder(1); // <5>

					assertThat(cart.getCartItems()).extracting(CartItem::getItem) //
							.containsExactly(new Item("item1", "TV tray", "Alf TV tray", 19.99)); // <6>

					return true; // <7>
				}) //
				.verifyComplete(); // <8>
	}
	// end::test2[]

}
