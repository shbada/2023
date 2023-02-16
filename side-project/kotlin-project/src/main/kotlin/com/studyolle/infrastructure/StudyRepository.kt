package com.studyolle.infrastructure

import com.studyolle.domain.Study
import org.springframework.data.jpa.repository.JpaRepository

interface StudyRepository: JpaRepository<Study, Long> {
    fun findStudyWithMembersByPath(path: String): Study
}