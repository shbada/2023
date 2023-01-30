package com.fastcampus.issueservice.web

import com.fastcampus.issueservice.config.AuthUser
import com.fastcampus.issueservice.domain.enums.IssueStatus
import com.fastcampus.issueservice.model.IssueRequest
import com.fastcampus.issueservice.service.IssueService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService,
) {

    /**
     * 이슈 등록
     * HandlerMethodArgumentResolver 를 사용해서 어노테이션 없이 authUser 사용 가능
     * Controller 인자에 authUser 이 있으면 HandlerMethodArgumentResolver 를 찾아서
     * supportsParameter 와 일치하는 객체가 있으면 resolveArgument()가 실행되어 authUser 에 객체가 들어온다.
     */
    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest,
    ) = issueService.create(authUser.userId, request)

    /**
     * 이슈 리스트 조회
     */
    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @RequestParam(required = false, defaultValue = "TODO") status : IssueStatus,
    ) = issueService.getAll(status)

    /**
     * 이슈 상세 조회
     */
    @GetMapping("/{id}")
    fun get(
        authUser: AuthUser,
        @PathVariable id: Long,
    ) = issueService.get(id)

    /**
     * 이슈 수정
     */
    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable id : Long,
        @RequestBody request: IssueRequest,
    ) = issueService.edit(authUser.userId, id, request)

    /**
     * 이슈 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete (
        authUser: AuthUser,
        @PathVariable id: Long,
    ) {
        issueService.delete(id)
    }

}