package com.member.api.presentation

import com.member.api.application.MemberService
import com.member.api.presentation.dto.MemberDto
import lombok.RequiredArgsConstructor
import org.clonecoder.member.interfaces.member.mapper.MemberDtoMapper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("")
    fun register(@RequestBody registerRequest: MemberDto.RegisterRequest): String {
        val memberCommand = MemberDtoMapper.of(registerRequest);
        memberService.register(memberCommand);
        return "";
    }
}