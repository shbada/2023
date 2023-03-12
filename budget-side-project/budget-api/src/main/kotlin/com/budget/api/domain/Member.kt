package com.budget.api.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Member (
    @Id
    @GeneratedValue
    val id: Long?= null,

    /* 중복 불가능 */
    @Column(unique = true)
    var email: String,

    var password: String,
) {

}