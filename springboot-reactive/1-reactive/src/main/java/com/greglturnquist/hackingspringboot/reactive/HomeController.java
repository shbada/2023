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
// tag::code[]
package com.greglturnquist.hackingspringboot.reactive;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping
	Mono<String> home() {
		// 템플릿의 이름을 나타내는 문자열을 리액티브 컨테이너인 Mono에 담아서 반환한다.

		/**
		 * Mono
		 * 0개 또는 1개의 원소만 담을 수 있는 리액티브 발행자(publisher)로서, 프로젝트 리액터에서 제공해주는 구현체다.
		 */
		return Mono.just("home");
	}
}
// end::code[]