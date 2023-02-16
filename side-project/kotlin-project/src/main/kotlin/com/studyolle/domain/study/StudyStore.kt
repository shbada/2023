package com.studyolle.domain.study

import com.studyolle.domain.Study
import java.util.*

interface StudyStore {
    fun createNewStudy(study: Study)
    fun getStudy(studyIdx: Long): Optional<Study>
    fun findStudyWithMembersByPath(path: String): Study?
}