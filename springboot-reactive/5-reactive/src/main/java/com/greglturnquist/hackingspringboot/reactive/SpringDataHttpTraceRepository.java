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

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public class SpringDataHttpTraceRepository implements HttpTraceRepository {

	private final HttpTraceWrapperRepository repository;

	/**
	 * HttpTraceWrapperRepository 인스턴스를 주입받는다.
	 * @param repository
	 */
	public SpringDataHttpTraceRepository(HttpTraceWrapperRepository repository) {
		this.repository = repository; // <1>
	}

	/**
	 * HttpTraceWrapper 클래스 스트림을 받아온후 getHttpTrace() 메서드를 매핑하고 리스트에 담아서 반환한다.
	 * @return
	 */
	@Override
	public List<HttpTrace> findAll() {
		return repository.findAll() //
				.map(HttpTraceWrapper::getHttpTrace) // <2>
				.collect(Collectors.toList());
	}

	/**
	 * HttpTrace 객체를 감싸는 HttpTraceWrapper 객체를 새로 생성하고, save() 인자로 전달하여 저장한다.
	 * HttpTrace 는 불변타입이다.
	 * @param trace
	 */
	@Override
	public void add(HttpTrace trace) {
		repository.save(new HttpTraceWrapper(trace)); // <3>
	}
}
// end::code[]
