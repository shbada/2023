package com.studyolle.common.response

import com.studyolle.common.exception.ErrorMessage
import org.springframework.http.ResponseEntity

class CommonResponse {
    companion object {
        fun send(): ResponseEntity<ResponseDto<Void>> {
            return ResponseEntity.ok().body(ResponseDto())
        }

        fun <T> send(items: T): ResponseEntity<ResponseDto<T>> {
            return ResponseEntity.ok().body(ResponseDto<T>().apply {
                data = items
            })
        }

        fun <T> send(errorMessage: ErrorMessage, items: T): ResponseEntity<ResponseDto<T>> {
            return ResponseEntity.badRequest().body(ResponseDto<T>().apply {
                code = errorMessage.code
                message = errorMessage.message
                data = items
            })
        }
    }
}