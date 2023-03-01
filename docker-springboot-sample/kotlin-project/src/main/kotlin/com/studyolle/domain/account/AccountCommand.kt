package com.studyolle.domain.account

import com.studyolle.domain.Account

class AccountCommand {
    data class SignUpForm(
        val nickname: String,
        val email: String,
        var password: String, // setter
    ) {
        fun toEntity() = Account(
            nickname = nickname,
            email = email,
            password = password,
        )
    }
}