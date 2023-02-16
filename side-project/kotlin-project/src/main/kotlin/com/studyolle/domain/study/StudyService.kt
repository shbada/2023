package com.studyolle.domain.study

import com.studyolle.common.exception.BadRequestException
import com.studyolle.common.exception.ErrorMessage
import com.studyolle.domain.Account
import com.studyolle.domain.Study
import com.studyolle.domain.account.AccountStore
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudyService(
    private val studyStore: StudyStore,
    private val accountStore: AccountStore
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun createNewStudy(registerForm: StudyCommand.RegisterForm) {
        /* get manager */
        val manager: Account = accountStore.getAccountByEmail(registerForm.email)
            ?: throw BadRequestException(ErrorMessage.NOT_EXIST_INFO)

        val study: Study = registerForm.toEntity()
        study.addManager(manager);

        /* 스터디 저장 */
        studyStore.createNewStudy(study)
    }

    fun getStudy(studyIdx: Long): Study {
        val study: Optional<Study> = studyStore.getStudy(studyIdx)

        if (study.isEmpty) {
            throw BadRequestException(ErrorMessage.NOT_EXIST_INFO)
        }

        return study.get()
    }

    fun findStudyWithMembersByPath(path: String): Study? {
        return studyStore.findStudyWithMembersByPath(path)
    }

    fun removeMember(study: Study, email: String) {
        val account = accountStore.getAccountByEmail(email)
            ?: throw BadRequestException(ErrorMessage.NOT_EXIST_INFO)

        study.removeMember(account)
    }
}