package com.official.cufitapi.domain.invitation.api.dto

import com.official.cufitapi.domain.member.domain.vo.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.member.domain.vo.MemberType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대코드 생성 요청")
data class InvitationCodeGenerateRequest(
    @Schema(description = "후보자, 주선자", example = "MATCHMAKER/CANDIDATE")
    val memberType: MemberType,
    @Schema(description = "후보자, 주선자의 관계", example = "FRIEND")
    val relationType: MatchMakerCandidateRelationType
)
