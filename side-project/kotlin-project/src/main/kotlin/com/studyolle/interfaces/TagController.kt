package com.studyolle.interfaces

import com.studyolle.application.TagFacade
import com.studyolle.interfaces.dto.TagDto
import com.studyolle.interfaces.mapper.TagDtoMapper
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/tag")
class TagController(
    private val tagFacade: TagFacade,
) {
    /**
     * 태그 등록
     */
    @PostMapping
    fun tagRegister(
        @RequestBody registerForm: TagDto.RegisterForm
    ) {
        tagFacade.findOrCreateNew(TagDtoMapper.of(registerForm))
    }
}