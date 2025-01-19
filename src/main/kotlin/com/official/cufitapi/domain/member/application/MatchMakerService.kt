package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchMakerJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberProfileImageJpaRepository
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

    fun availableCandidateCount(matchMakerId: Long): Long {
        val memberRelations = memberRelationJpaRepository.findByInviterId(matchMakerId)
        val inviteeIds = memberRelations.map { it.inviteeId }
        return matchCandidateJpaRepository.findAllByMemberIdIn(inviteeIds)
            .count(MatchCandidateEntity::hasProfile)
            .toLong()
    }

    fun availableCandidates(matchMakerId: Long): List<MatchCandidateEntity> {
        val memberRelations = memberRelationJpaRepository.findByInviterId(matchMakerId)
        val inviteeIds = memberRelations.map { it.inviteeId }
        return matchCandidateJpaRepository.findAllByMemberIdIn(inviteeIds)
            .filter(MatchCandidateEntity::hasProfile)
    }

    fun availableOtherCandidates(matchMakerId: Long): List<MatchCandidateEntity> {
        val memberRelations = memberRelationJpaRepository.findByInviterId(matchMakerId)
        val inviteeIds = memberRelations.map { it.inviteeId }
        return matchCandidateJpaRepository.findAllByMemberIdNotIn(inviteeIds)
            .filter(MatchCandidateEntity::hasProfile)
    }
}
