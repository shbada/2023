package com.studyolle.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Tag (
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val title: String? = null,
) {

}