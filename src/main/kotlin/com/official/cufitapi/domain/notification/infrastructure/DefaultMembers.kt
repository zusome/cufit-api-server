package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberRepository
import com.official.cufitapi.domain.notification.domain.Members
import org.springframework.stereotype.Component

@Component
class DefaultMembers(
    private val jpaMemberRepository: JpaMemberRepository,
) : Members {
    override fun name(memberId: Long): String {
        return jpaMemberRepository.findById(memberId)
            .orElseThrow { throw IllegalArgumentException("Member not found")}
            .name ?: "큐피트"
    }
}
