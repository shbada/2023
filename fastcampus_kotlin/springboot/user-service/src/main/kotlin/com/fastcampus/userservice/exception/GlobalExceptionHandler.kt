package com.fastcampus.userservice.exception

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

/**
 * ErrorWebExceptionHandler : reactive 에서 제공해주는 Handler
 */
@Configuration
class GlobalExceptionHandler(private val objectMapper: ObjectMapper) : ErrorWebExceptionHandler {

    private val logger = KotlinLogging.logger {}

    /**
     * 반환타입 Mono<Void>
     * 코루틴에서 코투린을 mono로 바꿔주는 mono 이름의 코루틴 빌더를 제공해준다. (mono : import kotlinx.coroutines.reactor.mono)
     */
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> = mono {
        // 에러 로그 출력
        logger.error { ex.printStackTrace() }

        val errorResponse = if (ex is ServerException) { // 우리가 정의한 오류인 경우
            ErrorResponse(code = ex.code, message = ex.message)
        } else { // 아닌 경우 메시지 지정 필요
            ErrorResponse(code = 500, message = "Internal Server Error")
        }

        // with 로 감싸기
        with(exchange.response) {
            statusCode = HttpStatus.OK
            headers.contentType = MediaType.APPLICATION_JSON

            // to DataBuffer
            val dataBuffer: DataBuffer = bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse))

            // ServerHttpResponse 의 writeWith 메서드
            // mono to coroutine
            writeWith(dataBuffer.toMono()).awaitSingle()
        }
    }


}