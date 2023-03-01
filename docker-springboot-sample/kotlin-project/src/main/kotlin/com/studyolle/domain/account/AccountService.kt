package com.studyolle.domain.account

import com.studyolle.domain.Account
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountStore: AccountStore,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 회원가입
     */
    fun processNewAccount(signUpForm: AccountCommand.SignUpForm) {
        /* 신규 회원 저장 */
        saveNewAccount(signUpForm)
    }

    private fun saveNewAccount(signUpForm: AccountCommand.SignUpForm) {
        /* password set */
        signUpForm.password = passwordEncoder.encode(signUpForm.password)

        /* account entity */
        val account: Account = signUpForm.toEntity()

        /* email token 생성 */
        account.generateEmailCheckToken()

        /* 이메일 토큰 발송 */
        this.sendSignUpConfirmEmail(account) // token 은 위 saveNewAccount 에서 저장

        accountStore.saveNewAccount(account)
    }

    /**
     * 토큰 검증
     */
    fun sendSignUpConfirmEmail(newAccount: Account) {
        /* email content */
        val contentMap = mutableMapOf<String, String>();

        contentMap["link"] = "/check-email-token?token=" + newAccount.emailCheckToken +
                "&email=" + newAccount.email
        contentMap["nickname"] = newAccount.nickname
        contentMap["linkName"] = "이메일 인증하기"
        contentMap["message"] = "스터디올래 서비스를 사용하려면 링크를 클릭하세요."

        /* send */
        emailService.sendEmail(contentMap.entries.joinToString())
    }

    fun completeSignUp(email: String, token: String) {
        /* email 로 회원 조회 */
        val account: Account? = accountStore.getAccountByEmail(email)

        /* check1. 이메일 유효 확인 */
        if (account == null) {
            throw Exception()
        }

        /* check2. 토큰 유효 확인 */
        if (!account.isValidToken(token)) {
            log.info("isValidToken false")
        }

        /* 계정의 이메일 인증 처리 */
        account.completeSignUp()
    }
}