package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.CandidateApiDocs
import com.official.cufitapi.domain.member.api.dto.CandidateProfileInfoResponse
import com.official.cufitapi.domain.member.api.dto.CandidateProfileResponse
import com.official.cufitapi.domain.member.api.dto.candidate.*
import com.official.cufitapi.domain.member.application.CandidateMatchBreakUseCase
import com.official.cufitapi.domain.member.application.CandidateProfileUpdateUseCase
import com.official.cufitapi.domain.member.application.command.candidate.CandidateMatchBreakCommand
import com.official.cufitapi.domain.member.application.command.candidate.CandidateProfileUpdateCommand
import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.domain.vo.MBTILetter
import com.official.cufitapi.domain.member.infrastructure.ProfileImageUploadClientAdapter
import com.official.cufitapi.domain.member.infrastructure.persistence.dao.CandidateDao
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class CandidateApi(
    private val candidateDao: CandidateDao,
    private val candidateProfileUpdateUseCase: CandidateProfileUpdateUseCase,
    private val candidateMatchBreakUseCase: CandidateMatchBreakUseCase,
    private val profileImageUploadClientAdapter: ProfileImageUploadClientAdapter
) : CandidateApiDocs {

    // 나한테 제안된 후보자 목록 조회
    @GetMapping("/candidates/suggestion")
    override fun getSuggestedCandidate(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<List<CandidateResponse>> {
        // TODO : CandidateDAO를 활용하여 구현
        return HttpResponse.of(
            HttpStatus.OK,
            mutableListOf(
                CandidateResponse(
                    id = 1L,
                    images = listOf(
                        CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 1),
                        CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 2)
                    ),
                    name = "John Doe",
                    yearOfBirth = "1990",
                    relation = "Friend",
                    makerName = "Jane Smith",
                    mbti = listOf(MBTILetter.I, MBTILetter.N, MBTILetter.T, MBTILetter.J),
                    height = 180,
                    city = "서울특별시",
                    district = "강남구",
                    job = "Developer",
                    hobbies = emptyList(),
                    smoke = 1,
                    drink = 1,
                )
            ),
        )
    }

    @GetMapping("/candidates/profile")
    override fun profile(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidateProfileInfoResponse> {
        val profile = candidateDao.profile(authorizationUser.userId)
        return HttpResponse.of(
            HttpStatus.OK,
            CandidateProfileInfoResponse(
                isCompleted = profile.isCompleted(),
                profile = CandidateProfileResponse(profile)
            )
        )
    }

    // 추천순 (우리가 정한 score) , 최신순

    // 후보자 프로필 업데이트 API
    @PostMapping("/candidates")
    override fun updateCandidateBasicProfile(
        @Authorization(AuthorizationType.BASIC, AuthorizationType.CANDIDATE)
        authorizationUser: AuthorizationUser,
        @RequestBody request: CandidateProfileUpdateRequest,
    ): HttpResponse<Unit> {
        candidateProfileUpdateUseCase.updateProfile(
            CandidateProfileUpdateCommand(
                images = request.images.images.map { CandidateImage(it.imageUrl, it.profileOrder)}.toMutableList(),
                memberId = authorizationUser.userId,
                gender = request.gender,
                yearOfBirth = request.yearOfBirth,
                height = request.height,
                job = request.job,
                city = request.city,
                district = request.district,
                mbti = request.mbti,
                idealHeightRange = request.idealHeightRange,
                idealAgeRange = request.idealAgeRange,
                idealMbti = request.idealMbti,
                phoneNumber = request.phoneNumber,
                hobbies = request.hobbies,
                smoke = request.smoke,
                drink = request.drink
            )
        )
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }

    @PostMapping("/candidates/match/break")
    override fun breakMatching(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: MatchBreakRequest,
    ): HttpResponse<Unit> {
        candidateMatchBreakUseCase.breakMatch(
            CandidateMatchBreakCommand(
                memberId = authorizationUser.userId,
                isMatchAgreed = request.isMatchAgreed
            )
        )
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }

    @GetMapping("/candidates/profile-images/presigned-url")
    override fun getProfileImageUploadPresignedUrl(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        )
        authorizationUser: AuthorizationUser
    ): HttpResponse<CandidatePresignedUrlUploadResponse> {
        return HttpResponse.of(HttpStatus.OK,
            CandidatePresignedUrlUploadResponse(
                presignedUrl = profileImageUploadClientAdapter.getImageUploadPresignedUrl()
            ))
    }
}
