package com.member.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
class MemeberApiApplication

fun main(args: Array<String>) {
    runApplication<MemeberApiApplication>(*args)
}
