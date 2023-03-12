package com.budget.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
class TestController {
    @GetMapping("")
    fun test(): String {
        return "hello member-project"
    }
}
