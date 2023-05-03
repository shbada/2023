package com.itvillage.example;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
//@Configuration
public class WebClientExample02 {
    @Bean
    public ApplicationRunner examplesWebClient02() {

        return (ApplicationArguments arguments) -> {
            exampleWebClient01();
//            exampleWebClient02();
        };
    }

    private void exampleWebClient01() {
        /**
         * WebClient는 특정 서버 엔진의 HTTP Client Connector 설정을 통해 HTTP Connection에 대한 Timeout을 설정할 수 있다.
         */
        HttpClient httpClient = // HTTP Client Connector 설정을 위한 HTTP Client 객체 생성
                HttpClient
                        .create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500) // Connection Timeout 설정
                        .responseTimeout(Duration.ofMillis(500)) // 응답을 수신하기까지의 Timeout 설정
                        .doOnConnected(connection -> // 파라미터로 전달되는 람다 표현식을 통해 Connection이 연결된 이후에 수행할 동작을 정의
                            connection
                                    // 각각 상황에 대한 핸들러 등록
                                    .addHandlerLast(
                                            new ReadTimeoutHandler(500,
                                                                TimeUnit.MILLISECONDS))
                                    .addHandlerLast(
                                            new WriteTimeoutHandler(500,
                                                                TimeUnit.MILLISECONDS)));

        Flux<BookDto.Response> response =
                WebClient
                        .builder()
                        .baseUrl("http://localhost:8080")
                        // Reactor Netty에서 제공하는 HttpClient 객체를 생성자 파라미터로 가지는 ReactorClientHttpConnector 를 설정
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build()
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/v10/books")
                                .queryParam("page", "1")
                                .queryParam("size", "10")
                                .build())
                        .retrieve()
                        .bodyToFlux(BookDto.Response.class);

        response
                .map(book -> book.getTitleKorean())
                .subscribe(bookName -> log.info("book name2: {}", bookName));
    }

    private void exampleWebClient02() {
        BookDto.Post post = new BookDto.Post("Java 중급",
                "Intermediate Java",
                "Java 중급 프로그래밍 마스터",
                "Kevin1", "333-33-3333-333-3",
                "2022-03-22");
        WebClient webClient = WebClient.create();
        webClient
                .post()
                .uri("http://localhost:8080/v10/books")
                .bodyValue(post)
                // exchangeToMono() 메서드를 이용해서 response를 사용자의 요구 조건에 맞게 제어할 수 있다.
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.CREATED))
                        return response.toEntity(Void.class);
                    else
                        return response
                                // request, response를 포함한 WebClientResponseException을 생성한다.
                                .createException()
                                .flatMap(throwable -> Mono.error(throwable));
                })
                /*
                위 코드에서 subscribe() 메서드는 WebClient의 HTTP 요청을 비동기적으로 실행하고,
                응답을 처리하는 콜백 함수를 등록하는 시점에 호출한다.
                 */
                .subscribe(res -> {
                    log.info("response status2: {}", res.getStatusCode());
                    log.info("Header Location2: {}", res.getHeaders().get("Location"));
                    },
                    error -> log.error("Error happened: ", error));
    }
}
