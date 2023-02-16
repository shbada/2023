package com.studyolle.common.exception

import com.studyolle.common.response.CommonResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionGlobalHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BadRequestException::class)
    fun domainException(e: BadRequestException): ResponseEntity<*> {
        return CommonResponse.send(e.errorMessage, null)
    }
}