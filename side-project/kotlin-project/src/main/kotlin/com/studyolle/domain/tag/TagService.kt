package com.studyolle.domain.tag

import com.studyolle.domain.Tag
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagStore: TagStore,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun findOrCreateNew(registerForm: TagCommand.RegisterForm) {
        val tag: Tag = registerForm.toEntity()
        tagStore.findOrCreateNew(tag)
    }
}