package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchMakerJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberProfileImageJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberRelationEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberRelationJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MatchMakerService(
    private val memberRelationJpaRepository: MemberRelationJpaRepository,
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository,
    private val matchMakerJpaRepository: MatchMakerJpaRepository,
    private val memberProfileImageJpaRepository: MemberProfileImageJpaRepository,
) {

    fun candidateCount(matchMakerId: Long): Long {
        val memberRelations = memberRelationJpaRepository.findByInviterId(matchMakerId)
        if(memberRelations.isEmpty()) {
            return 0L
        }
        val inviteeIds = memberRelations.map(MemberRelationEntity::inviteeId)
        return matchCandidateJpaRepository.countByMemberIdIn(inviteeIds)
    }

    fun candidates(matchMakerId: Long): List<MatchCandidateEntity> {
        val memberRelations = memberRelationJpaRepository.findByInviterId(matchMakerId)
        val inviteeIds = memberRelations.map { it.inviteeId }
        return matchCandidateJpaRepository.findAllByMemberIdIn(inviteeIds)
    }

    fun otherCandidates(matchMakerId: Long): List<MatchCandidateEntity> {
        val memberRelations = memberRelationJpaRepository.findByInviterId(matchMakerId)
        val inviteeIds = memberRelations.map { it.inviteeId }
        return matchCandidateJpaRepository.findAllByMemberIdNotIn(inviteeIds)
    }
}
