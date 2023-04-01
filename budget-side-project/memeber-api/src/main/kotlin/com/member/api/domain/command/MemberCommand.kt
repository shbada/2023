package org.clonecoder.core.domain.member

import com.member.api.domain.Member

class MemberCommand {
    data class RegisterMember(
        val email: String,
        val password: String,
    ) {
        fun toEntity() = Member(
            email = email,
            password = password
        )
    }
}