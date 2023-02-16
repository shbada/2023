package com.studyolle.interfaces.mapper

import com.studyolle.domain.study.StudyCommand
import com.studyolle.interfaces.dto.StudyDto

class StudyDtoMapper {
    companion object {
        fun of(registerForm: StudyDto.RegisterForm): StudyCommand.RegisterForm =
            StudyCommand.RegisterForm(
                email = registerForm.email,
                path = registerForm.path,
                title = registerForm.title,
                shortDescription = registerForm.shortDescription,
                fullDescription = registerForm.fullDescription,
            )
    }
}