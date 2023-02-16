package com.studyolle.application

import com.studyolle.common.exception.BadRequestException
import com.studyolle.common.exception.ErrorMessage
import com.studyolle.domain.Study
import com.studyolle.domain.study.StudyCommand
import com.studyolle.domain.study.StudyService
import com.studyolle.domain.tag.TagCommand
import com.studyolle.domain.tag.TagService
import org.springframework.stereotype.Service

@Service
class TagFacade(
    private val tagService: TagService,
) {
    fun findOrCreateNew(registerForm: TagCommand.RegisterForm) {
        tagService.findOrCreateNew(registerForm)
    }
}