package com.budget.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BudgetApiApplication

fun main(args: Array<String>) {
    runApplication<BudgetApiApplication>(*args)
}
