package com.official.cufitapi.domain.invitation.infrastructure

import com.official.cufitapi.domain.invitation.domain.Inviters
import com.official.cufitapi.domain.invitation.domain.vo.Inviter
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultInviters(
    private val jpaMemberRepository: JpaMemberRepository,
) : Inviters {

    @Transactional(readOnly = true)
    override fun findById(inviterId: Long): Inviter {
        return findByIdOrNull(inviterId) ?: throw IllegalStateException("Inviter not found")
    }

    @Transactional(readOnly = true)
    override fun findByIdOrNull(inviterId: Long): Inviter? =
        jpaMemberRepository.findByIdOrNull(inviterId)
            ?.let { Inviter(it.id!!, it.name ?: throw IllegalStateException("name is null")) }
}
