package com.official.cufitapi.domain.match.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.match.api.docs.MatchApiDocs
import com.official.cufitapi.domain.match.api.dto.SuggestMatchRequest
import com.official.cufitapi.domain.match.api.dto.SuggestMatchResponse
import com.official.cufitapi.domain.match.api.dto.UpdateMatchRequest
import com.official.cufitapi.domain.match.application.SuggestMatchUsecase
import com.official.cufitapi.domain.match.application.UpdateMatchUsecase
import com.official.cufitapi.domain.match.infrastructure.persistence.MatchDao
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@ApiV1Controller
@RestController
class MatchApi(
    private val suggestMatchUseCase: SuggestMatchUsecase,
    private val updateMatchUseCase: UpdateMatchUsecase,
    private val matchDao: MatchDao,
) : MatchApiDocs {

    @PostMapping("/matches")
    override fun suggestMatch(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: SuggestMatchRequest,
    ): HttpResponse<SuggestMatchResponse> {
        val matchId = suggestMatchUseCase.suggestMatch(request.toCommand(authorizationUser.userId))
        return HttpResponse.of(HttpStatus.CREATED, SuggestMatchResponse(matchId))
    }

    @PostMapping("/matches/{matchId}")
    override fun updateMatch(
        @PathVariable("matchId") matchId: Long,
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: UpdateMatchRequest,
    ): HttpResponse<Void> {
        updateMatchUseCase.nextStep(request.toCommand(matchId, authorizationUser.userId))
        return HttpResponse.of(HttpStatus.OK, null)
    }


    @GetMapping("/matches/candidates")
    override fun findAvailableCandidates(
        @RequestParam("targetId") targetId: Long,
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MatchDao.MatchCandidates> {
        return HttpResponse.of(
            HttpStatus.OK,
            matchDao.findAllByMatchId(authorizationUser.userId, targetId)
        )
    }
}
