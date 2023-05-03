package com.itvillage.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * WebClient
 * Spring5부터 지원하는 Non-Blocking HTTP request를 위한 리액티브 웹 클라이언트로서 함수형 기반의 향상된 API를 제공한다.
 * 내부적으로 HTTP 클라이언트 라이브러리에게 HTTP request를 위임하며, 기본 HTTP 클라이언트 라이브러리는 Reactor Netty다.
 * WebClient는 Non-Blocking과 Blocking HTTP request를 모두 지원하기 때문에 RestTemplate을 대체할 것으로 예상한다.
 */
@Slf4j
//@Configuration
public class WebClientExample01 {
    @Bean
    public ApplicationRunner examplesWebClient() {

        return (ApplicationArguments arguments) -> {
            // 총 4번의 request
            exampleWebClient01();
            exampleWebClient02();
            exampleWebClient03();
            exampleWebClient04();
        };
    }

    private void exampleWebClient01() {
        BookDto.Post requestBody = new BookDto.Post("Java 중급",
                "Intermediate Java",
                "Java 중급 프로그래밍 마스터",
                "Kevin1", "222-22-2222-222-2",
                "2022-03-22");

        WebClient webClient = WebClient.create();
        Mono<ResponseEntity<Void>> response =
                webClient
                        .post()
                        .uri("http://localhost:8080/v10/books")
                        .bodyValue(requestBody)
                        // response를 어떤 형태로 얻을지에 대한 프로세스의 시작을 선언
                        .retrieve()
                        .toEntity(Void.class); // 파라미터로 주어진 클래스의 형태로 변환한 response body가 포함된 ResponseEntity 객체를 리턴한다.

        response.subscribe(res -> {
           log.info("response status: {}", res.getStatusCode());
           log.info("Header Location: {}", res.getHeaders().get("Location"));
        });
    }

    private void exampleWebClient02() {
        BookDto.Patch requestBody =
                new BookDto.Patch.PatchBuilder().titleKorean("Java 고급")
                .titleEnglish("Advanced Java")
                .description("Java 고급 프로그래밍 마스터")
                .author("Tom")
                .build();

        WebClient webClient = WebClient.create("http://localhost:8080");
        Mono<BookDto.Response> response =
                webClient
                        .patch()
                        .uri("http://localhost:8080/v10/books/{book-id}", 20)
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(BookDto.Response.class);

        response.subscribe(book -> {
            log.info("bookId: {}", book.getBookId());
            log.info("titleKorean: {}", book.getTitleKorean());
            log.info("titleEnglish: {}", book.getTitleEnglish());
            log.info("description: {}", book.getDescription());
            log.info("author: {}", book.getAuthor());
        });
    }

    private void exampleWebClient03() {
        Mono<BookDto.Response> response =
                WebClient
                        .create("http://localhost:8080")
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/v10/books/{book-id}")
                                .build(21))
                                .retrieve()
                                .bodyToMono(BookDto.Response.class);

        response.subscribe(book -> {
            log.info("bookId: {}", book.getBookId());
            log.info("titleKorean: {}", book.getTitleKorean());
            log.info("titleEnglish: {}", book.getTitleEnglish());
            log.info("description: {}", book.getDescription());
            log.info("author: {}", book.getAuthor());
        });
    }

    private void exampleWebClient04() {
        Flux<BookDto.Response> response =
                WebClient
                        .create("http://localhost:8080")
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
                .subscribe(bookName -> log.info("book name: {}", bookName));
    }
}
