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

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
public class TemplateDatabaseLoader {

	/**
	 * MongoOperations
	 * - 블로킹 레포지토리를 사용하지 않고 블로킹 방식으로 데이터르 로딩하는 방법
	 * - 애플리케이션과 몽고DB의 결합도를 낮추기 위한 인터페이스
	 * - MongoTemplate (블로킹 버전), ReactiveMongoTemplate(비동기/논블로킹 버전)
	 * @param mongo
	 * @return
	 */
	@Bean
	CommandLineRunner initialize(MongoOperations mongo) {
		return args -> {
			// implements : MongoTemplate
			mongo.save(new Item("Alf alarm clock", 19.99));
			mongo.save(new Item("Smurf TV tray", 24.99));
		};
	}
}
// end::code[]
