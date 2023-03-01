package com.studyolle.infrastructure

import com.studyolle.domain.Tag
import com.studyolle.domain.tag.TagStore
import org.springframework.stereotype.Component

@Component
class TagStoreImpl(
    private val tagRepository: TagRepository
) : TagStore {
    override fun findOrCreateNew(tag: Tag) {
        tagRepository.save(tag)
    }
}