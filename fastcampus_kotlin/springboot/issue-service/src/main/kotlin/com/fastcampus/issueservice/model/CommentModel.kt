package com.fastcampus.issueservice.model

import com.fastcampus.issueservice.domain.Comment

/**
 * request
 */
data class CommentRequest(
    val body: String,
)

/**
 * response
 */
data class CommentResponse(
    val id: Long,
    val issueId: Long,
    val userId: Long,
    val body: String,
    val username: String? = null
)

/**
 * invoke 사용 대신, 이번에는 메서드를 생성해보자.
 */
fun Comment.toResponse() = CommentResponse(
    id = id!!,
    issueId = issue.id!!,
    userId = userId,
    body = body,
    username = username,
)