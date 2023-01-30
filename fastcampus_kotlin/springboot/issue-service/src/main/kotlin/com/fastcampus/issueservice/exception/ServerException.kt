package com.fastcampus.issueservice.exception

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)
/* @ExceptionHandler(ServerException::class) 를 탄다 */
data class NotFoundException(
    override val message: String,
) : ServerException(404, message) // sealed class 상속

data class UnauthorizedException(
    override val message: String = "인증 정보가 잘못되었습니다", // default message
) : ServerException(401, message)