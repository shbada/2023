package com.itvillage.v11;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BookRouter {
    @Bean
    public RouterFunction<?> routeBook(BookHandler handler) {
        return route()
                .POST("/v11/books", handler::createBook)
                .PATCH("/v11/books/{book-id}", handler::updateBook)
                .GET("/v11/books", handler::getBooks)
                .GET("/v11/books/{book-id}", handler::getBook)
                .build();
    }

    @Bean
    public RouterFunction<?> routeStreamingBook(BookService bookService,
                                                BookMapper mapper) {
        return route(RequestPredicates.GET("/v11/streaming-books"),
                request -> ServerResponse
                        .ok()
                        // SSE를 이용해 이벤트 스트림을 클라이언트로 전송하기 위한 Content Type 지정
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(bookService
                                        .streamingBooks() // Flux<Book> 리턴
                                        .map(book -> mapper.bookToResponse(book))
                                ,
                                // 두번째 파라미터 : emit되는 데이터 타입 지정 (response body type)
                                BookDto.Response.class)); // 최종적으로 Flux<BookDto.Response>
    }
}
