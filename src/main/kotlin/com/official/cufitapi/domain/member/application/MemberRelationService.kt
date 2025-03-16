package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.domain.MemberRelation
import com.official.cufitapi.domain.member.domain.repository.MemberRelationRepository
import org.springframework.stereotype.Service

interface RegisterMemberRelationUseCase {
    fun register(inviterId: Long, inviteeId: Long, relationType: String): MemberRelation
}

@Service
class MemberRelationService(
    private val memberRelationRepository: MemberRelationRepository
): RegisterMemberRelationUseCase {

    override fun register(inviterId: Long, inviteeId: Long, relationType: String): MemberRelation {
        return memberRelationRepository.save(MemberRelation(inviterId, inviteeId, relationType))
    }
}
