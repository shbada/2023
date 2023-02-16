package com.studyolle.interfaces

import com.studyolle.application.AccountFacade
import com.studyolle.interfaces.dto.AccountDto
import com.studyolle.interfaces.mapper.AccountDtoMapper
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account")
class AccountController(
    private val accountFacade: AccountFacade,
) {
    /**
     * 회원가입
     */
    @PostMapping
    fun signUpSubmit(
        @RequestBody signUpForm: AccountDto.SignUpForm
    ) {
        accountFacade.processNewAccount(AccountDtoMapper.of(signUpForm))
    }

    /**
     * 토큰 검증
     */
    @GetMapping("/check_eamil_token")
    fun checkEmailToken(
       token : String,
       email : String
    ) {
        accountFacade.completeSignUp(email, token)
    }
}