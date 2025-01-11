package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.MatchMakerApiDocs
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateCountResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidatesInfoResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidatesInfoResponseDto
import com.official.cufitapi.domain.member.api.dto.candidate.OtherCandidatesInfoResponse
import com.official.cufitapi.domain.member.api.dto.candidate.OtherCandidatesInfoResponseDto
import com.official.cufitapi.domain.member.application.MatchMakerService
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class MatchMakerApi(
    private val matchMakerService: MatchMakerService
) : MatchMakerApiDocs {

    // 본인이 등록한 후보자 개수 조회
    @GetMapping("/matchmakers/candidates/count")
    fun candidateCount(
        @Authorization(AuthorizationType.MATCHMAKER) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidateCountResponse> {
        val candidateCount = matchMakerService.candidateCount(authorizationUser.userId)
        return HttpResponse.of(HttpStatus.OK, CandidateCountResponse(candidateCount))
    }

    // 본인이 등록한 후보자 정보 반환
    @GetMapping("/matchmakers/candidates")
    fun candidates(
        @Authorization(AuthorizationType.MATCHMAKER) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidatesInfoResponse> {
        val candidates = matchMakerService.candidates(authorizationUser.userId)
        val candidateInfoResponse = CandidatesInfoResponse(candidates.map(::mapToCandidates))
        return HttpResponse.of(HttpStatus.OK, candidateInfoResponse)
    }

    // 상대방이 등록한 후보자 정보 반환
    @GetMapping("/matchmakers/other/candidates")
    fun otherCandidates(
        @Authorization(AuthorizationType.MATCHMAKER) authorizationUser: AuthorizationUser,
    ): HttpResponse<OtherCandidatesInfoResponse> {
        val otherCandidates = matchMakerService.otherCandidates(authorizationUser.userId)
        val otherCandidateInfoResponse = OtherCandidatesInfoResponse(otherCandidates.map(::mapToOtherCandidates))
        return HttpResponse.of(HttpStatus.OK, otherCandidateInfoResponse)
    }

    private fun mapToCandidates(matchCandidateEntity: MatchCandidateEntity) =
        CandidatesInfoResponseDto(
            id = matchCandidateEntity.id!!,
            memberId = matchCandidateEntity.member.id!!,
            isMatchingAgreed = matchCandidateEntity.isMatchingAgreed,
            idealMbti = matchCandidateEntity.idealMbti,
            idealAgeRange = matchCandidateEntity.idealAgeRange,
            idealHeightRange = matchCandidateEntity.idealHeightRange,
            height = matchCandidateEntity.height,
            station = matchCandidateEntity.station,
            job = matchCandidateEntity.job,
            name = matchCandidateEntity.name,
            yearOfBirth = matchCandidateEntity.yearOfBirth,
            email = matchCandidateEntity.email
        )

    private fun mapToOtherCandidates(matchCandidateEntity: MatchCandidateEntity) =
        OtherCandidatesInfoResponseDto(
            id = matchCandidateEntity.id!!,
            memberId = matchCandidateEntity.member.id!!,
            isMatchingAgreed = matchCandidateEntity.isMatchingAgreed,
            idealMbti = matchCandidateEntity.idealMbti,
            idealAgeRange = matchCandidateEntity.idealAgeRange,
            idealHeightRange = matchCandidateEntity.idealHeightRange,
            height = matchCandidateEntity.height,
            station = matchCandidateEntity.station,
            job = matchCandidateEntity.job,
            name = matchCandidateEntity.name,
            yearOfBirth = matchCandidateEntity.yearOfBirth,
            email = matchCandidateEntity.email
        )
}
