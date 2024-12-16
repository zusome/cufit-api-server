package com.official.cufitapi.domain.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.infrastructure.repository.MatchCandidateJpaRepository
import com.official.cufitapi.domain.infrastructure.repository.MatchMakerJpaRepository
import com.official.cufitapi.domain.infrastructure.repository.MemberProfileImageJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class MatchMakerService(
    private val matchMakerJpaRepository: MatchMakerJpaRepository,
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository,
    private val memberProfileImageJpaRepository: MemberProfileImageJpaRepository,
    private val makerCandidateRelationJpaRepository: MatchCandidateJpaRepository
) {

    fun getCandidates(memberId: Long, matchMakerId: Long) {
        // makerId 받기.
        val matchMaker = matchMakerJpaRepository.findByIdOrNull(matchMakerId)
            ?: throw InvalidRequestException("존재하지 않는 주선자입니다.")

        if (matchMaker.memberId != memberId) {
            throw InvalidRequestException("주선자의 권한이 없습니다.")
        }
        // 후보자 목록 (후보자와 주선자의 관계)
        // 각 후보자의 connection
        // makercandidate relation, connection
    }

}