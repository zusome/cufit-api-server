package com.official.cufitapi.domain.member.infrastructure.persistence.dto

import com.official.cufitapi.domain.member.domain.vo.MatchMakerCandidateRelationType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "사용자 정보(초대자 관계) 응답")
data class MemberInfoResponse(
    @Schema(description = "이름", example = "우지")
    val name: String?,
    @Schema(description = "이메일", example = "unan@unan.unan")
    val email: String?,
    @Schema(description = "초대자 이름", example = "유난")
    val inviterName: String,
    @Schema(description = "초대자 관계", example = "FRIEND,FAMILY,COMPANION,ACQUAINTANCE")
    val relationWithInvitee: MatchMakerCandidateRelationType
)
