package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.MatchMakerApiDocs
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateCountResponse
import com.official.cufitapi.domain.member.application.MatchMakerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class MatchMakerApi(
    private val matchMakerService: MatchMakerService
) : MatchMakerApiDocs {

    // 본인이 등록한 후보자 개수 조회
    @GetMapping("/matchmakers/candidates/count")
    fun getCandidateCount(
        @Authorization(AuthorizationType.MATCHMAKER) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidateCountResponse> {
        val candidateCount = matchMakerService.getCandidateCount(authorizationUser.userId)
        return HttpResponse.of(HttpStatus.OK, CandidateCountResponse(candidateCount))
    }
}
