package com.fastcampus.issueservice.service

import com.fastcampus.issueservice.domain.Issue
import com.fastcampus.issueservice.domain.IssueRepository
import com.fastcampus.issueservice.domain.enums.IssueStatus
import com.fastcampus.issueservice.exception.NotFoundException
import com.fastcampus.issueservice.model.IssueRequest
import com.fastcampus.issueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {

    /**
     * 이슈 등록
     */
    @Transactional
    fun create(userId: Long, request: IssueRequest) : IssueResponse {

        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )
        return IssueResponse(issueRepository.save(issue))
    }

    /**
     * 이슈 리스트 조회
     */
    @Transactional(readOnly = true)
    fun getAll(status: IssueStatus) =
        // map 을 통해서 건별로 IssueResponse 로 변환한다.
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            ?.map { IssueResponse(it) }

    /**
     * 이슈 상세 조회
     */
    @Transactional(readOnly = true)
    fun get(id: Long): IssueResponse {
        /* 확장함수 findByIdOrNull */
        val issue = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")
        return IssueResponse(issue)
    }

    /**
     * 이슈 수정
     */
    @Transactional
    fun edit(userId: Long, id: Long, request: IssueRequest) : IssueResponse {
        val issue: Issue = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")

        // update
        return with(issue) {
            summary = request.summary
            description = request.description
            this.userId = userId
            type  = request.type
            priority = request.priority
            status = request.status
            IssueResponse(issueRepository.save(this)) // 명시적으로 써준다.
        }
    }

    /**
     * 이슈 삭제
     */
    fun delete(id: Long) {
        issueRepository.deleteById(id)
    }

}