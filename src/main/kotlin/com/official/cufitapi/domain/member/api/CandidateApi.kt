package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.CandidateApiDocs
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateProfileUpdateRequest
import com.official.cufitapi.domain.member.application.CandidateProfileUpdateUseCase
import com.official.cufitapi.domain.member.application.command.candidate.CandidateProfileUpdateCommand
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchMakerDao
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class CandidateApi(
    private val matchMakerDao: MatchMakerDao,
    private val candidateProfileUpdateUseCase: CandidateProfileUpdateUseCase,
) : CandidateApiDocs {

    // 추천순 (우리가 정한 score) , 최신순

    // 후보자 프로필 업데이트 API
    @PostMapping("/candidates")
    override fun updateCandidateProfile(
        @Authorization(AuthorizationType.CANDIDATE) authorizationUser: AuthorizationUser,
        @RequestBody request: CandidateProfileUpdateRequest,
    ): HttpResponse<Unit> {
        candidateProfileUpdateUseCase.updateProfile(
            CandidateProfileUpdateCommand(
                memberId = authorizationUser.userId,
                name = request.name,
                gender = request.gender,
                yearOfBirth = request.yearOfBirth,
                height = request.height,
                job = request.job,
                station = request.station,
                mbti = request.mbti,
                idealHeightRange = request.idealHeightRange,
                idealAgeRange = request.idealAgeRange,
                idealMbti = request.idealMbti,
                email = request.email,
                phoneNumber = request.phoneNumber
            )
        )
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }

}
