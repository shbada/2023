package com.studyolle.common.response

class ResponseDto<T> {
    var code: String = "00"
    var message: String? = null
    var data: T? = null
}