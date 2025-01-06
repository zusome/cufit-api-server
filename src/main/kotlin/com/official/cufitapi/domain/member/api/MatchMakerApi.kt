package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.MatchMakerApiDocs
import com.official.cufitapi.domain.member.application.MatchMakerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiV1Controller
class MatchMakerApi(
    private val matchMakerService: MatchMakerService
) : MatchMakerApiDocs {

    // 본인이 등록한 후보자 목록 조회
    @GetMapping("/matchmakers/{matchMakerId}/candidates")
    fun getRegisteredCandidates(
        @PathVariable matchMakerId: Long,
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
    ): HttpResponse<Unit> {
        // matchMakerService.getCandidates(matchMakerId)
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }
}