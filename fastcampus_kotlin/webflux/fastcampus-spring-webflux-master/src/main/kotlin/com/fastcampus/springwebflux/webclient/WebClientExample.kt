package com.fastcampus.springwebflux.webclient

import com.fastcampus.springwebflux.book.Book
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@RestController
class WebClientExample {

    val url = "http://localhost:8080/books"

    val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/books/block")
    fun getBooksBlockingWay(): List<Book> {
        log.info("Start RestTemplate")
        val restTemplate = RestTemplate()

        // restTemplate 단점 : 응답을 받을때까지 쓰레드 블로킹
        // restTemplate.exchange() 호출하여 통신했을때 응답이 오래걸리면 그 시간동안 다른 일을 하지 못한다.
        val response = restTemplate.exchange(url, HttpMethod.GET, null,
            object : ParameterizedTypeReference<List<Book>>() {})

        val result = response.body!!
        log.info("result : {}", result)
        log.info("Finish RestTemplate")
        return result
    }

    @GetMapping("/books/nonblock")
    fun getBooksNonBlockingWay(): Flux<Book> {
        log.info("Start WebClient")

        // 논블로킹 호출
        // 결과가 받아지기 전에, 이후 로직이 수행된다.
        val flux = WebClient.create()
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Book::class.java)
            .map {
                log.info("result : {}", it)
                it
            }

        log.info("Finish WebClient")
        return flux
    }


}