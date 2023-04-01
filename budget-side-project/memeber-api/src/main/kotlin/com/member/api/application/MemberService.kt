package com.member.api.application

import com.member.api.infrastrucrue.MemberRepository
import com.member.api.presentation.dto.MemberDto
import lombok.RequiredArgsConstructor
import org.clonecoder.core.domain.member.MemberCommand
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun register(memberCommand: MemberCommand.RegisterMember) {
        val member = memberCommand.toEntity();
        memberRepository.save(member);
    }
}