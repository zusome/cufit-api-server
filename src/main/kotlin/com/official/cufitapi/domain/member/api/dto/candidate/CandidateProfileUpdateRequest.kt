package com.official.cufitapi.domain.member.api.dto.candidate

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.member.domain.vo.Gender
import com.official.cufitapi.domain.member.domain.vo.IdealAge
import com.official.cufitapi.domain.member.domain.vo.IdealHeightUnit
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(name = "후보자 프로필 업데이트 요청")
data class CandidateProfileUpdateRequest(
    @Schema(description = "이미지", example = "{ \"imageUrl\": \"https://cataas.com/cat\", \"profileOrder\": 1 }")
    val images: CandidateImagesRequest,
    @Schema(description = "성별", example = "MALE,FEMALE")
    val gender: Gender,
    @Schema(description = "생년", example = "1990")
    val yearOfBirth: Int,
    @Schema(description = "키", example = "170")
    val height: Int,
    @Schema(description = "직업", example = "Male")
    val job: String,
    @Schema(description = "가까운역", example = "봉천역")
    val station: String,
    @Schema(description = "MBTI", example = "ESFP")
    val mbti: String,
    @Schema(description = "이상형 키 범위", example = "RANGE150, RANGE160")
    val idealHeightRange: List<IdealHeightUnit>,
    @Schema(description = "이상형 나이", example = "EQUAL,OLDER")
    val idealAgeRange: List<IdealAge>,
    @Schema(description = "이상형 MBTI", example = "ESFP")
    val idealMbti: String,
    @Schema(description = "핸드폰 번호", example = "010-1234-5678")
    val phoneNumber: String
) {
    init {
        if (yearOfBirth >= LocalDateTime.now().year) {
            throw InvalidRequestException("생년은 올해 이전 값이어야 합니다. : $yearOfBirth")
        }
    }
}
