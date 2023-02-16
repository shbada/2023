package com.studyolle.domain.account

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Slf4j
class EmailService {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    fun sendEmail(message : String) {
        log.info("sent email: {}", message)
    }
}