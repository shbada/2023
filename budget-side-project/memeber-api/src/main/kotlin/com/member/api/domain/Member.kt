package com.member.api.domain

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Column
import javax.persistence.Id

@Document(collection = "member")
class Member (
) {
    @Id
    val id: String? = null

    /* 중복 불가능 */
    var email: String = ""

    var password: String = ""

    constructor(email: String, password: String) : this() {
        this.email = email
        this.password = password
    }
}