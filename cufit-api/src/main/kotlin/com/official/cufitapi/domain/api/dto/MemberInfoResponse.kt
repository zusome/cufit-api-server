package com.official.cufitapi.domain.api.dto

import com.official.cufitapi.domain.enums.MatchMakerCandidateRelationType
import io.swagger.v3.oas.annotations.media.Schema


@Schema(name = "사용자 정보 응답")
data class MemberInfoResponse(
    @Schema(name = "이름")
    val name: String,
    @Schema(name = "email")
    val email: String,
    @Schema(name = "초대자 이름")
    val inviteeName: String,
    @Schema(name = "초대한 사람과의 관계")
    val relationWithInvitee: MatchMakerCandidateRelationType
)
