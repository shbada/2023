package com.studyolle.domain

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class Account (
    @Id
    @GeneratedValue
    val id: Long?= null,

    /* 중복 불가능 */
    @Column(unique = true)
    var email: String,

    @Column(unique = true)
    var nickname: String,
    var password: String,

    /* 이메일 검증 */
    var emailVerified : Boolean = false,
    var emailCheckToken: String? = null,
    var emailCheckTokenGeneratedAt: LocalDateTime? = null,

    // 가입일자
    var joinedAt : LocalDateTime? = null,

    /* account_tags 테이블 생성 */
    @ManyToMany
    val tags: Set<Tag> = HashSet(),
) {
    fun generateEmailCheckToken() {
        emailCheckToken = UUID.randomUUID().toString()
        emailCheckTokenGeneratedAt = LocalDateTime.now()
    }

    fun isValidToken(token: String): Boolean {
        return emailCheckToken == token
    }

    fun completeSignUp(){
        emailVerified = true
        joinedAt = LocalDateTime.now()
    }
}
