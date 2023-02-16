package com.studyolle.interfaces

import com.studyolle.application.StudyFacade
import com.studyolle.common.response.CommonResponse
import com.studyolle.domain.Study
import com.studyolle.interfaces.dto.StudyDto
import com.studyolle.interfaces.mapper.StudyDtoMapper
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/study")
class StudyController(
    private val studyFacade: StudyFacade,
) {
    /**
     * 스터디 등록
     */
    @PostMapping
    fun studyRegister(
        @RequestBody registerForm: StudyDto.RegisterForm
    ) {
        studyFacade.createNewStudy(StudyDtoMapper.of(registerForm))
    }

    /**
     * 스터디 단건조회
     */
    @GetMapping("/{studyIdx}")
    fun getStudy(
        @PathVariable studyIdx: Long
    ): ResponseEntity<*> {
        return CommonResponse.send(studyFacade.getStudy(studyIdx))
    }

    /**
     * 스터디 탈퇴
     * TODO Entity 의존성 제거
     */
    @PostMapping("/study/{path}/leave")
    fun leaveStudy(@PathVariable path: String, @RequestBody leaveForm: StudyDto.LeaveForm): ResponseEntity<*> {
        /* 스터디 조회 */
        val study: Study = studyFacade.findStudyWithMembersByPath(leaveForm.path)

        /* 스터디 탈퇴 */
        studyFacade.removeMember(study, leaveForm.email)

        return CommonResponse.send()
    }
}