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

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Service
class AltInventoryService {

	private ItemRepository itemRepository;

	private CartRepository cartRepository;

	AltInventoryService(ItemRepository repository, CartRepository cartRepository) {
		this.itemRepository = repository;
		this.cartRepository = cartRepository;
	}

	public Mono<Cart> getCart(String cartId) {
		return this.cartRepository.findById(cartId);
	}

	public Flux<Item> getInventory() {
		return this.itemRepository.findAll();
	}

	Mono<Item> saveItem(Item newItem) {
		return this.itemRepository.save(newItem);
	}

	Mono<Void> deleteItem(String id) {
		return this.itemRepository.deleteById(id);
	}

	/**
	 * 블로킹 호출을 포함
	 * @param cartId
	 * @param itemId
	 * @return
	 */
	// tag::blocking[]
	Mono<Cart> addItemToCart(String cartId, String itemId) {
		Cart myCart = this.cartRepository.findById(cartId) //
				.defaultIfEmpty(new Cart(cartId)) //
				.block();

		return myCart.getCartItems().stream() //
				.filter(cartItem -> cartItem.getItem().getId().equals(itemId)) //
				.findAny() //
				.map(cartItem -> {
					cartItem.increment();
					return Mono.just(myCart);
				}) //
				.orElseGet(() -> this.itemRepository.findById(itemId) //
						.map(item -> new CartItem(item)) //
						.map(cartItem -> {
							myCart.getCartItems().add(cartItem);
							return myCart;
						})) //
				.flatMap(cart -> this.cartRepository.save(cart));
	}
	// end::blocking[]

	Mono<Cart> removeOneFromCart(String cartId, String itemId) {
		return this.cartRepository.findById(cartId) //
				.defaultIfEmpty(new Cart(cartId)) //
				.flatMap(cart -> cart.getCartItems().stream() //
						.filter(cartItem -> cartItem.getItem().getId().equals(itemId)) //
						.findAny() //
						.map(cartItem -> {
							cartItem.decrement();
							return Mono.just(cart);
						}) //
						.orElse(Mono.empty())) //
				.map(cart -> new Cart(cart.getId(), cart.getCartItems().stream() //
						.filter(cartItem -> cartItem.getQuantity() > 0) //
						.collect(Collectors.toList()))) //
				.flatMap(cart -> this.cartRepository.save(cart));
	}
}
// end::code[]
