package com.official.cufitapi.domain.arrangement.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.arrangement.api.docs.ArrangementApiDocs
import com.official.cufitapi.domain.arrangement.api.dto.SuggestArrangementRequest
import com.official.cufitapi.domain.arrangement.api.dto.SuggestArrangementResponse
import com.official.cufitapi.domain.arrangement.api.dto.UpdateArrangementRequest
import com.official.cufitapi.domain.arrangement.application.SuggestArrangementUsecase
import com.official.cufitapi.domain.arrangement.application.UpdateArrangementUsecase
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementDao
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementDao.ArrangementCandidate
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Profile("!local")
@ApiV1Controller
@RestController
class ArrangementMockApi(
    private val suggestArrangementUseCase: SuggestArrangementUsecase,
    private val updateArrangementUseCase: UpdateArrangementUsecase,
    private val arrangementDao: ArrangementDao,
) : ArrangementApiDocs {

    @PostMapping("/arrangements")
    override fun suggestArrangement(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: SuggestArrangementRequest,
    ): HttpResponse<SuggestArrangementResponse> {
        // val arrangementId = suggestArrangementUseCase.suggestArrangement(request.toCommand(authorizationUser.userId))
        return HttpResponse.of(HttpStatus.CREATED, SuggestArrangementResponse(1L))
    }

    @PostMapping("/arrangements/{arrangementId}")
    override fun updateArrangement(
        @PathVariable("arrangementId") arrangementId: Long,
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: UpdateArrangementRequest,
    ): HttpResponse<Void> {
        // updateArrangementUseCase.updateArrangement(request.toCommand(arrangementId))
        return HttpResponse.of(HttpStatus.OK, null)
    }

    @GetMapping("/arrangements/candidates")
    override fun findAvailableCandidates(
        @RequestParam("targetId") targetId: Long,
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<ArrangementDao.ArrangementCandidates> {
        return HttpResponse.of(
            HttpStatus.OK,
            ArrangementDao.ArrangementCandidates(
                listOf(
                    ArrangementCandidate(
                        image = "https://search.pstatic.net/common?type=b&size=3000&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F202301%2F20230127132729112.jpg",
                        "차은우",
                        "직장 동료",
                        29,
                        1
                    ),
                    ArrangementCandidate(
                        image = "https://search.pstatic.net/common?type=b&size=3000&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2FprofileImg%2Fe27fbdf2-be0c-43c6-9add-b836279a80b5.jpg",
                        "추영우",
                        "직장 동료",
                        27,
                        0
                    ),
                    ArrangementCandidate(
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
