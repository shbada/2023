package com.fastcampus.userservice.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fastcampus.userservice.config.JWTProperties
import java.util.*

object JWTUtils {

    /**
     * JWT 토큰 생성
     */
    fun createToken(claim: JWTClaim, properties: JWTProperties) =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresTime * 1000))
            .withClaim("userId", claim.userId)
            .withClaim("email", claim.email)
            .withClaim("profileUrl", claim.profileUrl)
            .withClaim("username", claim.username)
            .sign(Algorithm.HMAC256(properties.secret))

    fun decode(token: String, secret: String, issuer: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secret)

        // require 사용하여 to JWTVerifier
        val verifier: JWTVerifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build()

        // decode
        return verifier.verify(token)
    }

}

/**
 * JWT 담을 정보
 */
data class JWTClaim(
    val userId: Long,
    val email: String,
    val profileUrl: String? = null,
    val username: String,
)