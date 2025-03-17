package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.MakerApiDocs
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerCandidateCountResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerCandidatesResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerOtherCandidatesCountResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerOtherCandidatesResponse
import com.official.cufitapi.domain.member.api.mapper.MatchMakerApiDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.dao.MatchMakerDao
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class MakerApi(
    @Qualifier("matchMakerDaoJdbcTemplateDao")private val matchMakerDao: MatchMakerDao,
    private val matchMakerApiResponseMapper: MatchMakerApiDtoMapper,
) : MakerApiDocs {

    @GetMapping("/matchmakers/candidates/count")
    override fun findCandidatesCount(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MatchMakerCandidateCountResponse> {
        val candidateCount = matchMakerDao.candidateCount(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            matchMakerApiResponseMapper.candidateCount(candidateCount)
        )
    }

    @GetMapping("/matchmakers/candidates/others/count")
    override fun findOtherCandidatesCount(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MatchMakerOtherCandidatesCountResponse> {
        val otherCandidateCount = matchMakerDao.otherCandidateCount(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            matchMakerApiResponseMapper.otherCandidateCount(otherCandidateCount)
        )
    }

    @GetMapping("/matchmakers/candidates")
    override fun findCandidates(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MatchMakerCandidatesResponse> {
        val matchCandidates = matchMakerDao.findCandidates(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            matchMakerApiResponseMapper.matchCandidates(matchCandidates)
        )
    }

    @GetMapping("/matchmakers/candidates/others")
    override fun findOtherCandidates(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MatchMakerOtherCandidatesResponse> {
        val otherCandidates = matchMakerDao.findOtherCandidates(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            matchMakerApiResponseMapper.matchOtherCandidates(otherCandidates)
        )
    }
}
