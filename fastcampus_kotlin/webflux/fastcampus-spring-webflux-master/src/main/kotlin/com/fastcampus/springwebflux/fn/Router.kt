package com.fastcampus.springwebflux.fn

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class Router {

    @Bean // RouterFunction 를 반환하는 빈 등록
    fun helloRouter(handler: HelloHandler): RouterFunction<ServerResponse> =
        route()
            .GET("/", handler::sayHello)
            .build()


    @Bean
    fun userRouter(handler: UserHandler): RouterFunction<ServerResponse> =
        router {
            "/users".nest { // 중첩 라우터
                GET("/users/{id}", handler::getUser)
                GET("/users", handler::getAll)
            }
        }

}
