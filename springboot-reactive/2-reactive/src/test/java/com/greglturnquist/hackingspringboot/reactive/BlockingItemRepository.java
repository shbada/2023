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

import org.springframework.data.repository.CrudRepository;

/**
 * ReactiveCrudRepository -> CrudRepository
 * 블로킹 레포지토리 사용
 *
 * (구동 시점에 데이터 로딩)
 * 애플리케이션이 시작하는 과정에서 문제가 될 수 있다.
 * 네티(Netty)가 시작되면 구독자(subscriber)가 애플리케이션 시작 스레드로 하여금
 * 이벤트 루프를 데드락 상태에 빠드릴 수 있는 위험이 있다.
 * 애플리케이션 시작 시점에만 발생하는 이슈이며, 지금처럼 테스트 데이터를 로딩하는 테스트 환경 구성에서는
 * 약간의 블로킹 코드를 사용해도 문제가 되지 않는다. 물론, 블로킹 코드는 실제 운영환경에선 절대로 사용하면 안된다.
 * @author Greg Turnquist
 */
// tag::code[]
interface BlockingItemRepository extends CrudRepository<Item, String> {

}
// end::code[]
