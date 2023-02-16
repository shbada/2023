package com.studyolle.infrastructure

import com.studyolle.domain.Study
import com.studyolle.domain.study.StudyStore
import org.springframework.stereotype.Component
import java.util.*

@Component
class StudyStoreImpl(
    private val studyRepository: StudyRepository
) : StudyStore {
    override fun createNewStudy(study: Study) {
        studyRepository.save(study)
    }

    override fun getStudy(studyIdx: Long): Optional<Study> {
        return studyRepository.findById(studyIdx)
    }

    override fun findStudyWithMembersByPath(path: String): Study {
        return studyRepository.findStudyWithMembersByPath(path)
    }
}