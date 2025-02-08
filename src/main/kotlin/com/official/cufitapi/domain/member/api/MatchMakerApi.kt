package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.MatchMakerApiDocs
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateCountResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateImage
import com.official.cufitapi.domain.member.api.dto.candidate.CandidatesInfoResponseDto
import com.official.cufitapi.domain.member.api.dto.candidate.OtherCandidatesCountResponse
import com.official.cufitapi.domain.member.api.dto.candidate.OtherCandidatesInfoResponseDto
import com.official.cufitapi.domain.member.application.MatchMakerService
import com.official.cufitapi.domain.member.infrastructure.persistence.dao.MatchMakerDao
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class MatchMakerApi(
    private val matchMakerDao: MatchMakerDao,
    private val matchMakerService: MatchMakerService
) : MatchMakerApiDocs {

    @GetMapping("/matchmakers/candidates")
    override fun findCandidates(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MatchMakerDao.MatchCandidates> {
        return HttpResponse.of(
            HttpStatus.OK,
            MatchMakerDao.MatchCandidates(
                listOf(
                    MatchMakerDao.MatchCandidate(
                        image = "https://cataas.com/cat",
                        name = "김철수",
                        relation = "친구",
                        arrangements = listOf(),
                        hasProfile = true,
                        isMatchingPaused = false
                    ),
                    MatchMakerDao.MatchCandidate(
                        image = "https://cataas.com/cat",
                        name = "홍길동",
                        relation = "직장동료",
                        arrangements = listOf(
                            MatchMakerDao.ArrangementInfo(
                                profileImage = "https://cataas.com/cat",
                                dateName = "장충동",
                                arrangementStatus = "성공"
                            ),
                            MatchMakerDao.ArrangementInfo(
                                profileImage = "https://cataas.com/cat",
                                dateName = "박혁거세",
                                arrangementStatus = "실패"
                            ),
                            MatchMakerDao.ArrangementInfo(
                                profileImage = "https://cataas.com/cat",
                                dateName = "박동충",
                                arrangementStatus = "대기"
                            )
                        ),
                        hasProfile = true,
                        isMatchingPaused = false
                    ),
                    MatchMakerDao.MatchCandidate(
                        image = "https://cataas.com/cat",
                        name = "박혁거세",
                        relation = "직장동료",
                        arrangements = listOf(),
                        hasProfile = false,
                        isMatchingPaused = false
                    ),
                    MatchMakerDao.MatchCandidate(
                        image = "https://cataas.com/cat",
                        name = "김민지",
                        relation = "친구",
                        arrangements = listOf(),
                        hasProfile = true,
                        isMatchingPaused = true
                    )
                )
            )
        )
    }

    @GetMapping("/matchmakers/candidates/count")
    override fun findCandidatesCount(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidateCountResponse> {
        return HttpResponse.of(
            HttpStatus.OK,
            CandidateCountResponse(8)
        )
    }

    @GetMapping("/matchmakers/candidates/others/count")
    override fun findOtherCandidatesCount(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<OtherCandidatesCountResponse> {
        return HttpResponse.of(
            HttpStatus.OK,
            OtherCandidatesCountResponse(100)
        )
    }

    // 상대 후보자 목록 조회 API
    @GetMapping("/matchmakers/candidates/others")
    override fun findOtherCandidates(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MatchMakerDao.OtherMatchCandidates> {
        return HttpResponse.of(
            HttpStatus.OK,
            MatchMakerDao.OtherMatchCandidates(
                listOf(
                    MatchMakerDao.OtherMatchCandidate(
                        id = 1L,
                        images = listOf(
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 1),
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 2)
                        ),
                        name = "홍길동",
                        yearOfBirth = 1997,
                        mbti = "ESFP",
                        height = 165,
                        station = "정자역",
                        job = "디자이너",
                        degrees = 2,
                        matchMakerRelation = "직장동료",
                        matchMakerName = "김철수",
                        idealHeightRange = listOf(170, 190),
                        idealAgeRange = listOf("연상", "연하", "동갑"),
                        idealMbti = listOf("외향적", "직관적", "계획형", "즉흥형")
                    )
                )
            )
        )
    }

    // // 본인이 등록한 후보자 개수 조회
    // @GetMapping("/matchmakers/candidates/count")
    // fun candidateCount(
    //     @Authorization(AuthorizationType.MATCHMAKER) authorizationUser: AuthorizationUser,
    // ): HttpResponse<CandidateCountResponse> {
    //     val candidateCount = matchMakerService.availableCandidateCount(authorizationUser.userId)
    //     return HttpResponse.of(HttpStatus.OK, CandidateCountResponse(candidateCount))
    // }

    // // 본인이 등록한 후보자 정보 반환
    // @GetMapping("/matchmakers/candidates")
    // fun candidates(
    //     @Authorization(AuthorizationType.MATCHMAKER) authorizationUser: AuthorizationUser,
    // ): HttpResponse<CandidatesInfoResponse> {
    //     val candidates = matchMakerService.availableCandidates(authorizationUser.userId)
    //     val candidateInfoResponse = CandidatesInfoResponse(candidates.map(::mapToCandidates))
    //     return HttpResponse.of(HttpStatus.OK, candidateInfoResponse)
    // }
    //
    // // 상대방이 등록한 후보자 정보 반환
    // @GetMapping("/matchmakers/other/candidates")
    // fun otherCandidates(
    //     @Authorization(AuthorizationType.MATCHMAKER) authorizationUser: AuthorizationUser,
    // ): HttpResponse<OtherCandidatesInfoResponse> {
    //     val otherCandidates = matchMakerService.availableOtherCandidates(authorizationUser.userId)
    //     val otherCandidateInfoResponse = OtherCandidatesInfoResponse(otherCandidates.map(::mapToOtherCandidates))
    //     return HttpResponse.of(HttpStatus.OK, otherCandidateInfoResponse)
    // }

    private fun mapToCandidates(matchCandidateEntity: MatchCandidateEntity) =
        CandidatesInfoResponseDto(
            id = matchCandidateEntity.id!!,
            memberId = matchCandidateEntity.member.id!!,
            isMatchAgreed = matchCandidateEntity.isMatchAgreed,
            idealMbti = matchCandidateEntity.idealMbti,
            idealAgeRange = matchCandidateEntity.idealAgeRange,
            idealHeightRange = matchCandidateEntity.idealHeightRange,
            height = matchCandidateEntity.height,
            station = matchCandidateEntity.station,
            job = matchCandidateEntity.job,
            yearOfBirth = matchCandidateEntity.yearOfBirth,
            gender = matchCandidateEntity.gender?.name,
            phoneNumber = matchCandidateEntity.phoneNumber
        )

    private fun mapToOtherCandidates(matchCandidateEntity: MatchCandidateEntity) =
        OtherCandidatesInfoResponseDto(
            id = matchCandidateEntity.id!!,
            memberId = matchCandidateEntity.member.id!!,
            isMatchAgreed = matchCandidateEntity.isMatchAgreed,
            idealMbti = matchCandidateEntity.idealMbti,
            idealAgeRange = matchCandidateEntity.idealAgeRange,
            idealHeightRange = matchCandidateEntity.idealHeightRange,
            height = matchCandidateEntity.height,
            station = matchCandidateEntity.station,
            job = matchCandidateEntity.job,
            yearOfBirth = matchCandidateEntity.yearOfBirth,
            gender = matchCandidateEntity.gender?.name,
            phoneNumber = matchCandidateEntity.phoneNumber
        )
}
