package com.studyolle.domain.tag

import com.studyolle.domain.Tag

class TagCommand {
    data class RegisterForm(
        val tagTitle: String,
    ) {
        fun toEntity() = Tag(
            title = tagTitle,
        )
    }
}