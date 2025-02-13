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
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@ApiV1Controller
@RestController
class ArrangementApi(
    private val suggestArrangementUseCase: SuggestArrangementUsecase,
    private val updateArrangementUseCase: UpdateArrangementUsecase,
    private val arrangementDao: ArrangementDao,
) : ArrangementApiDocs {

    @PostMapping("/arrangements")
    override fun suggestArrangement(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: SuggestArrangementRequest,
    ): HttpResponse<SuggestArrangementResponse> {
        val arrangementId = suggestArrangementUseCase.suggestArrangement(request.toCommand())
        return HttpResponse.of(HttpStatus.CREATED, SuggestArrangementResponse(arrangementId))
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
        updateArrangementUseCase.updateArrangement(request.toCommand(arrangementId))
        return HttpResponse.of(HttpStatus.OK, null)
    }


    @GetMapping("/arrangements/candidates")
    override fun findAvailableCandidates(
        @RequestParam("targetId") targetId: Long,
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<ArrangementDao.ArrangementCandidates> {
        return HttpResponse.of(
            HttpStatus.OK,
            arrangementDao.findAllByArrangementId(authorizationUser.userId, targetId)
        )
    }
}
