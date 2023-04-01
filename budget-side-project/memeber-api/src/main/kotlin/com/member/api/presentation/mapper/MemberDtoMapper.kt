package org.clonecoder.member.interfaces.member.mapper

import com.member.api.presentation.dto.MemberDto
import org.clonecoder.core.domain.member.MemberCommand


class MemberDtoMapper {
    companion object {
        fun of(request: MemberDto.RegisterRequest): MemberCommand.RegisterMember =
            MemberCommand.RegisterMember(
                email = request.email,
                password = request.password,
            )
    }
}