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
import com.official.cufitapi.domain.match.infrastructure.persistence.MatchDao.MatchCandidate
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// @Profile("!local")
// @ApiV1Controller
// @RestController
class MatchMockApi(
    private val suggestMatchUseCase: SuggestMatchUsecase,
    private val updateMatchUseCase: UpdateMatchUsecase,
    private val matchDao: MatchDao,
) : MatchApiDocs {

    @PostMapping("/matches")
    override fun suggest(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: SuggestMatchRequest,
    ): HttpResponse<SuggestMatchResponse> {
        // val matchId = suggestMatchUseCase.suggestMatch(request.toCommand(authorizationUser.userId))
        return HttpResponse.of(HttpStatus.CREATED, SuggestMatchResponse(1L))
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
        // updateMatchUseCase.updateMatch(request.toCommand(matchId))
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
            MatchDao.MatchCandidates(
                listOf(
                    MatchCandidate(
                        1L,
                        image = "https://search.pstatic.net/common?type=b&size=3000&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F202301%2F20230127132729112.jpg",
                        "차은우",
                        "직장 동료",
                        29,
                        1
                    ),
                    MatchCandidate(
                        2L,
                        image = "https://search.pstatic.net/common?type=b&size=3000&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2FprofileImg%2Fe27fbdf2-be0c-43c6-9add-b836279a80b5.jpg",
                        "추영우",
                        "직장 동료",
                        27,
                        0
                    ),
                    MatchCandidate(
                        3L,
                        image = "https://search.pstatic.net/common?type=b&size=3000&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F202009%2F20200910113051466.jpg",
                        "변우석",
                        "친구",
                        27,
                        2
                    )
                )
            )
        )
    }
}
