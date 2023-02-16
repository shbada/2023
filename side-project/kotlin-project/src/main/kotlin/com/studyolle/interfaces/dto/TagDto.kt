package com.studyolle.interfaces.dto

import javax.validation.constraints.NotBlank

class TagDto {
    data class RegisterForm(
        @NotBlank
        var tagTitle: String
    )
}