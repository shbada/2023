package com.member.api.infrastrucrue

import com.member.api.domain.Member
import org.springframework.data.mongodb.repository.MongoRepository

interface MemberRepository : MongoRepository<Member?, String?>