package com.fastcampus.springdatar2dbc

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

/**
 * ReactiveCrudRepository
 */
interface BookRepository : ReactiveCrudRepository<Book, Long> {

    fun findByName(name: String): Mono<Book>

}