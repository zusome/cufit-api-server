package com.official.cufitapi.domain.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.domain.api.docs.CandidateApiDocs
import com.official.cufitapi.domain.api.dto.candidate.CandidateProfileUpdateRequest
import com.official.cufitapi.domain.application.CandidateProfileUpdateUseCase
import com.official.cufitapi.domain.application.command.candidate.CandidateProfileUpdateCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class CandidateApi(
    private val candidateProfileUpdateUseCase: CandidateProfileUpdateUseCase
) : CandidateApiDocs {

    // 추천순 (우리가 정한 score) , 최신순

    // 후보자 프로필 업데이트 API
    @PostMapping("/candidates")
    fun updateCandidateProfile(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: CandidateProfileUpdateRequest
    ): ResponseEntity<Unit> {
        candidateProfileUpdateUseCase.update(
            CandidateProfileUpdateCommand(
                memberId = memberId,
                name = request.name,
                gender = request.gender,
                yearOfBirth = request.yearOfBirth,
                height = request.height,
                job = request.job,
                station = request.station,
                mbti = request.mbti,
                idealHeightRange = request.idealHeightRange,
                idealAgeRange = request.idealAgeRange,
                idealMbti = request.idealMbti
            )
        )
        return ResponseEntity.noContent().build()
    }


}