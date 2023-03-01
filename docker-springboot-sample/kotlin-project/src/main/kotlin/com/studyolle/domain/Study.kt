package com.studyolle.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Study(
    @Id
    @GeneratedValue
    private val id: Long? = null,

    /* study_managers 테이블 생성 */
    @ManyToMany
    val managers: MutableSet<Account> = HashSet(),

    /* study_members 테이블 생성 */
    @ManyToMany
    val members: MutableSet<Account> = HashSet(),

    @Column(unique = true)
    val path: String? = null,
    val title: String? = null,
    val shortDescription: String? = null,

    @Lob
    @Basic(fetch = FetchType.EAGER)
    val fullDescription: String? = null,

    /* study_tags 테이블 생성 */
    @ManyToMany
    val tags: Set<Tag> = HashSet(),

    var memberCount : Int = 0,
) {
    fun addManager(account: Account?) {
        members.add(account!!)
    }

    fun removeMember(account: Account?) {
        this.members.remove(account)
        this.memberCount--
    }
}