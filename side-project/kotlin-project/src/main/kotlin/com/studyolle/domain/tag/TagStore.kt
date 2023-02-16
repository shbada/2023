package com.studyolle.domain.tag

import com.studyolle.domain.Tag

interface TagStore {
    fun findOrCreateNew(tag: Tag)
}