package com.studyolle.interfaces.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class AccountDto {
    data class SignUpForm(
        @NotBlank
        @Length(min = 3, max = 20)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
        var nickname: String,

        @NotBlank
        @Email
        val email: String,

        @NotBlank
        @Length(min = 8, max = 50)
        val password: String,
    )
}