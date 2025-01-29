package com.official.cufitapi.domain.member.api.dto

import com.official.cufitapi.domain.member.domain.vo.Gender
import com.official.cufitapi.domain.member.domain.vo.IdealAge
import com.official.cufitapi.domain.member.domain.vo.IdealHeightUnit
import com.official.cufitapi.domain.member.domain.vo.MBTILetter
import com.official.cufitapi.domain.member.domain.vo.MemberType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "사용자 프로필 작성 요청")
data class MemberProfileRequest(
    @Schema(description = "사용자 타입", example = "BASIC,MATCHMAKER,CANDIDATE")
    val memberType: MemberType,
    @Schema(description = "이름", example = "홍길동")
    val name: String,
    @Schema(description = "성별", example = "MALE")
    val gender: Gender,
    @Schema(description = "출생년도", example = "1990")
    val yearOfBirth: Int,
    @Schema(description = "거주지", example = "관악구")
    val location: String,
    @Schema(description = "키", example = "180")
    val height: Int,
    @Schema(description = "MBTI", example = "ENTP")
    val MBTI: String,
    @Schema(description = "이상형 키 범위", example = "[HEIGHT_170,HEIGHT_175]")
    val idealHeightRange: List<IdealHeightUnit>,
    @Schema(description = "이상형 나이 범위", example = "[OLDER,EQUAL]")
    val idealAgeRange: List<IdealAge>,
    @Schema(description = "이상형 MBTI", example = "[E,N,T,P]")
    val idealMbti: List<MBTILetter>
)