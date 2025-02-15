package com.official.cufitapi.domain.member.api.dto.matchmaker

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "후보자 수 응답")
data class MatchMakerCandidateCountResponse(
    @Schema(description = "후보자 수")
    val count: Long
)
