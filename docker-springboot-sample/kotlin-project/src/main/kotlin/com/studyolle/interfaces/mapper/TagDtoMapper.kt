package com.studyolle.interfaces.mapper

import com.studyolle.domain.tag.TagCommand
import com.studyolle.interfaces.dto.TagDto

class TagDtoMapper {
    companion object {
        fun of(registerForm: TagDto.RegisterForm): TagCommand.RegisterForm =
            TagCommand.RegisterForm(
                tagTitle = registerForm.tagTitle
            )
    }
}