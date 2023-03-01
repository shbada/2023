package com.studyolle.interfaces.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class StudyDto {
    data class RegisterForm(
        @NotBlank
        @Email
        var email: String,

        @NotBlank
        @Length(min = 2, max = 20)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,20}$")
        var path: String,

        @NotBlank
        @Length(max = 50)
        val title: String,

        @NotBlank
        @Length(max = 100)
        val shortDescription: String,

        @NotBlank
        val fullDescription: String,
    )

    data class LeaveForm(
        @NotBlank
        @Email
        var email: String,

        @NotBlank
        @Length(min = 2, max = 20)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,20}$")
        var path: String,
    )
}