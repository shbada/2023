package com.fastcampus.userservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * application yml 파일의 설정을 그대로 넣을 예정
 */
@ConstructorBinding // .yml
@ConfigurationProperties(prefix = "jwt") // jwt:
data class JWTProperties(
    val issuer: String,
    val subject: String,
    val expiresTime: Long,
    val secret: String,
)