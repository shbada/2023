package com.studyolle.common.exception

class BadRequestException (
    var errorMessage: ErrorMessage,
): RuntimeException(errorMessage.message) {

}