package com.member.api.presentation.dto

class MemberDto {
    data class RegisterRequest(
        val email: String,
        val password: String
    )
}