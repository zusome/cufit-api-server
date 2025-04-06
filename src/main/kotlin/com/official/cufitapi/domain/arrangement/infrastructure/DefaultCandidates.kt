package com.official.cufitapi.domain.arrangement.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.arrangement.domain.Candidates
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaCandidateRepository
import org.springframework.stereotype.Component

@Component
class DefaultCandidates(
    private val candidateRepository: JpaCandidateRepository,
) : Candidates {

    override fun isSameGender(leftCandidateId: Long, rightCandidateId: Long): Boolean {
        val leftCandidate = candidateRepository.findByMemberId(leftCandidateId)
            ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_RECEIVER)
        val rightCandidate = candidateRepository.findByMemberId(rightCandidateId)
            ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_SENDER)
        return leftCandidate.isSameGender(rightCandidate)
    }
}
