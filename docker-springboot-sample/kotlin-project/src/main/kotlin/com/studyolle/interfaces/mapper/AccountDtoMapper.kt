package com.studyolle.interfaces.mapper

import com.studyolle.domain.account.AccountCommand
import com.studyolle.interfaces.dto.AccountDto

class AccountDtoMapper {
    companion object {
        fun of(signUpForm: AccountDto.SignUpForm): AccountCommand.SignUpForm =
            AccountCommand.SignUpForm(
                nickname = signUpForm.nickname,
                email = signUpForm.email,
                password = signUpForm.password
            )
    }
}