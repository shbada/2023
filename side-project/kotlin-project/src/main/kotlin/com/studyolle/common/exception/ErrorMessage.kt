package com.studyolle.common.exception

enum class ErrorMessage (
    val code: String,
    val message: String
) {
    NOT_EXIST_INFO("4001", "해당 정보는 존재하지 않습니다."),
}