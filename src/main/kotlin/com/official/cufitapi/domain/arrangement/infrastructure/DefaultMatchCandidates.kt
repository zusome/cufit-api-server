package com.official.cufitapi.domain.arrangement.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.arrangement.domain.MatchCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMatchCandidateRepository
import org.springframework.stereotype.Component

@Component
class DefaultMatchCandidates(
    private val matchCandidateRepository: JpaMatchCandidateRepository,
) : MatchCandidates {

    override fun isSameGender(leftCandidateId: Long, rightCandidateId: Long): Boolean {
        val leftCandidate = matchCandidateRepository.findByMemberId(leftCandidateId)
            ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_RECEIVER)
        val rightCandidate = matchCandidateRepository.findByMemberId(rightCandidateId)
            ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_SENDER)
        return leftCandidate.isSameGender(rightCandidate)
    }
}
