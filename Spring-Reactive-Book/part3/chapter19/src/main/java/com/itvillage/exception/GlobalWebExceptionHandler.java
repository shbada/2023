package com.itvillage.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvillage.book.v10.ErrorResponse;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Error Operator로 처리되지 않는 Exception을 글로벌 수준에서 처리하기 위한 Global Exception Handler 코드
 */
/*
SpringBoot의 ErrorWebFluxAutoConfiguration을 통해 등록된 DefaultErrorWebExceptionHandler Bean의 우선순위보다 높은 우선순위인 -2를 지정
 */
@Order(-2)
@Configuration
public class GlobalWebExceptionHandler implements ErrorWebExceptionHandler { // ErrorWebExceptionHandler 을 구현함으로써 Global Exception Handler로서 동작
    private final ObjectMapper objectMapper;

    public GlobalWebExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * ErrorWebExceptionHandler의 추상메서드 handle() 구현
     */
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange,
                             Throwable throwable) {
        // 전달받은 ServerWebExchange로 Response 설정 가능
        // 두번째 파라미터로 발생한 예외 처리
        return handleException(serverWebExchange, throwable);
    }

    private Mono<Void> handleException(ServerWebExchange serverWebExchange,
                                       Throwable throwable) {
        ErrorResponse errorResponse = null;
        DataBuffer dataBuffer = null;

        // bufferFactory() : BufferFactory 인터페이스의 구현 객체를 생성
        // BufferFactory는 DataBuffer를 위한 팩토리로써, response body를 write 하는데 사용한다.
        DataBufferFactory bufferFactory =
                                serverWebExchange.getResponse().bufferFactory();
        serverWebExchange.getResponse().getHeaders()
                                        .setContentType(MediaType.APPLICATION_JSON);

        // 비즈니스 로직 처리 중 발생하는 Exception과 Validation 처리 중 발생하는 Exception을 구분하여 ErrorResponse를 구성
        if (throwable instanceof BusinessLogicException) {
            BusinessLogicException ex = (BusinessLogicException) throwable;
            ExceptionCode exceptionCode = ex.getExceptionCode();
            errorResponse = ErrorResponse.of(exceptionCode.getStatus(),
                                                exceptionCode.getMessage());
            serverWebExchange.getResponse()
                        .setStatusCode(HttpStatus.valueOf(exceptionCode.getStatus()));
        } else if (throwable instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) throwable;
            errorResponse = ErrorResponse.of(ex.getStatus().value(), ex.getMessage());
            serverWebExchange.getResponse().setStatusCode(ex.getStatus());
        } else {
            errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                            throwable.getMessage());
            serverWebExchange.getResponse()
                                    .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            // ErrorResponse 객체를 DataBuffer로 래핑하여 response body로 구성
            dataBuffer =
                    bufferFactory.wrap(objectMapper.writeValueAsBytes(errorResponse));
        } catch (JsonProcessingException e) {
            bufferFactory.wrap("".getBytes());
        }

        // writeWith() : 파라미터로 지정한 Mono로 response body를 write
        return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
    }
}
