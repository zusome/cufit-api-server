package com.official.cufitapi.domain.member.api.dto.candidate

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "후보자 응답")
data class CandidateMatchSuggestionResponse(
    @Schema(description = "후보자 ID", example = "10")
    val id: Long,
    @Schema(description = "후보자 프로필 이미지 목록")
    val images: List<CandidateImage>,
    @Schema(description = "후보자 이름", example = "박유나")
    val name: String,
    @Schema(description = "후보자 나이", example = "25")
    val yearOfBirth: Int,
    @Schema(description = "주선자와의 관계", example = "직장동료")
    val makerRelation: String,
    @Schema(description = "매칭 주선자 이름", example = "김민지")
    val makerName: String,
    @Schema(description = "MBTI", example = "INTJ")
    val mbti: String,
    @Schema(description = "키", example = "170")
    val height: Int,
    @Schema(description = "도시", example = "강남역")
    val city: String,
    @Schema(description = "구역", example = "강남역")
    val district: String,
    @Schema(description = "직업", example = "개발자")
    val job: String,
    @Schema(description = "취미", example = "[\"독서\",\"운동\"]")
    val hobbies: List<String>,
    @Schema(description = "흡연 여부", example = "0")
    val smoke: Int,
    @Schema(description = "음주 여부", example = "0")
    val drink: Int,
    @Schema(description = "이상형 키 범위", example = "[160, 180]")
    val idealHeightRange: List<Int>,
    @Schema(description = "이상형 나이 범위", example = "[\"20대 초반\", \"30대 중반\"]")
    val idealAgeRange: List<String>,
    @Schema(description = "이상형 MBTI", example = "[\"INTJ\", \"INFP\"]")
    val idealMbti: List<String>,
    @Schema(description = "매치 식별자", example = "1")
    val matchId: Long,
    @Schema(description = "매치 남은 시간(UNIX_TIMESTAMP)", example = "MATCHED")
    val expiredTime: Long,
)
