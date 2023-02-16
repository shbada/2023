package com.studyolle.domain.study

import com.studyolle.domain.Study

class StudyCommand {
    data class RegisterForm(
        val email: String,
        val path: String,
        val title: String,
        var shortDescription: String,
        var fullDescription: String,
    ) {
        fun toEntity() = Study(
            path = path,
            title = title,
            shortDescription = shortDescription,
            fullDescription = fullDescription,
        )
    }
}