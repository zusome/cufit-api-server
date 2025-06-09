package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.MakerApiDocs
import com.official.cufitapi.domain.member.api.dto.maker.CandidateCountResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidatesResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidatesCountResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidatesResponse
import com.official.cufitapi.domain.member.api.mapper.MakerApiDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.dao.MakerDao
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class MakerApi(
    private val makerDao: MakerDao,
    private val makerApiResponseMapper: MakerApiDtoMapper,
) : MakerApiDocs {

    @GetMapping("/makers/candidates/count")
    override fun findCandidatesCount(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidateCountResponse> {
        val candidateCount = makerDao.candidateCount(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            makerApiResponseMapper.candidateCount(candidateCount)
        )
    }

    @GetMapping("/makers/candidates/others/count")
    override fun findOtherCandidatesCount(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MakerOtherCandidatesCountResponse> {
        val otherCandidateCount = makerDao.otherCandidateCount(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            makerApiResponseMapper.otherCandidateCount(otherCandidateCount)
        )
    }

    @GetMapping("/makers/candidates")
    override fun findCandidates(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MakerCandidatesResponse> {
        val candidates = makerDao.findCandidates(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            makerApiResponseMapper.candidates(candidates)
        )
    }

    @GetMapping("/makers/candidates/others")
    override fun findOtherCandidates(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MakerOtherCandidatesResponse> {
        val otherCandidates = makerDao.findOtherCandidates(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            makerApiResponseMapper.matchOtherCandidates(otherCandidates)
        )
    }
}
