package com.greglturnquist.hackingspringboot.reactive;

import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class HackingSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackingSpringBootApplication.class, args);
	}

	/**
	 * HttpTraceRepository 타입의 빈이 애플리케이션 컨텍스트에 등록되면 스프링 부트 액추에이터가 이를 감지하고,
	 * /actuator/httptrace 엔드포인트를 자동으로 활성화한다.
	 * 그리고 이를 스프링 웹플럭스가 연동해서 모든 웹 요청을 추적하고 로그를 남긴다.
	 *
	 * - 트레이스 정보는 현재 인스턴스에만 존재한다. 로드밸런서 뒤에 여러대의 인스턴스가 존재한다면,
	 * 인스턴스마다 자기 자신에게 들어온 요청에 대한 트레이스 정보가 생성된다.
	 * - 현재 인스턴스를 재시작하면 그동안의 트레이스 정보는 모두 소멸된다.
	 * @return
	 */
	// tag::http-trace[]
	HttpTraceRepository traceRepository() { // <2>
		return new InMemoryHttpTraceRepository(); // <3>
	}
	// end::http-trace[]

	/**
	 * - 애플리케이션이 재시작되더라도 트레이스 정보는 유지돼야한다.
	 * - 모든 인스턴스에서 발생하는 트레이스 정보가 중앙화된 하나의 데이터 스토어에 저장돼야한다.
	 *
	 * 스프링 부트 액추에이터의 HttpTraceRepository는 트레이스 정보를 HttpTrace 인스턴스에 담아서 저장한다.
	 * HttpTrace에는 키로 사용할 속성이 없어서 몽고디비에 바로 저장할 수 없다.
	 * 게다가 HttpTrace는 final로 선언돼있어 상속받아 새로운 클래스를 만들어 사용할 수도 없다.
	 * 따라서 HttpTrace를 감싸는 새로운 래퍼 클래스를 만들어서 몽고DB에 저장돼야한다.
	 * @param repository
	 * @return
	 */
	// tag::spring-data-trace[]
	@Bean
	HttpTraceRepository springDataTraceRepository(HttpTraceWrapperRepository repository) {
		return new SpringDataHttpTraceRepository(repository);
	}
	// end::spring-data-trace[]

	/**
	 * HttpTrace 객체는 불변 타입이다.
	 * 생성자를 사용해서 인스턴슬르 만든 후 세터 메서드로 속성값을 지정할 수 없으므로, 역직렬화하는 방법이 생성자에 마련되어야 한다.
	 * document에서 HttpRrace 레코드를 추출하고 정보를 읽어서 HttpTraceWrapper 객체를 생성하고 반환한다.
	 *
	 * 새 스프링 컨버터를 스프링 데이터 몽고DB에 등록하려면 MappingMongoConverter 빈을 생성해야한다.
	 * -> mappingMongoConverter
	 */
	// tag::custom-1[]
	static Converter<Document, HttpTraceWrapper> CONVERTER = //
			new Converter<Document, HttpTraceWrapper>() { //
				@Override
				public HttpTraceWrapper convert(Document document) {
					Document httpTrace = document.get("httpTrace", Document.class);
					Document request = httpTrace.get("request", Document.class);
					Document response = httpTrace.get("response", Document.class);

					return new HttpTraceWrapper(new HttpTrace( //
							new HttpTrace.Request( //
									request.getString("method"), //
									URI.create(request.getString("uri")), //
									request.get("headers", Map.class), //
									null),
							new HttpTrace.Response( //
									response.getInteger("status"), //
									response.get("headers", Map.class)),
							httpTrace.getDate("timestamp").toInstant(), //
							null, //
							null, //
							httpTrace.getLong("timeTaken")));
				}
			};
	// end::custom-1[]

	/**
	 * 새 스프링 컨버터를 스프링 데이터 몽고DB에 등록하려면 MappingMongoConverter 빈을 생성해야한다.
	 * @param context
	 * @return
	 */
	// tag::custom-2[]
	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoMappingContext context) {

		MappingMongoConverter mappingConverter = //
				new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, context); // <1>

		mappingConverter.setCustomConversions( // <2>
				new MongoCustomConversions(Collections.singletonList(CONVERTER))); // <3>

		return mappingConverter;
	}
	// end::custom-2[]

}
