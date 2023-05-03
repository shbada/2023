package com.itvillage.book.v8;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration("bookRouterV8")
public class BookRouter {
    /**
     * RounterFunction은 @RequestMapping 애너테이션과 동일한 기능을 한다고 생각하자.
     * @param handler
     * @return
     */
    @Bean
    public RouterFunction<?> routeBookV8(BookHandler handler) {
        return route() // RouterFunctionBuilder 객체 리턴
                // 각 요청에 매치되는 request를 처리하기 위한 route 추가
                // 두번째 파라미터 : 매치되는 request를 처리할 HandlerFunction 객체
                .POST("/v8/books", handler::createBook)
                .PATCH("/v8/books/{book-id}", handler::updateBook)
                .GET("/v8/books", handler::getBooks)
                .GET("/v8/books/{book-id}", handler::getBook)
                .build(); // 최종적으로 RouterFunction 생성
    }
}
