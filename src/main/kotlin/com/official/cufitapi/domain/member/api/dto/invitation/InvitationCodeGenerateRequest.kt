package com.official.cufitapi.domain.member.api.dto.invitation

import com.official.cufitapi.domain.member.enums.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.member.enums.MemberType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대코드 생성 요청")
data class InvitationCodeGenerateRequest(
    @Schema(description = "후보자, 주선자", example = "MATCHMAKER/CANDIDATE")
    val memberType: MemberType,
    @Schema(description = "후보자, 주선자의 관계", example = "FRIEND")
    val relationType: MatchMakerCandidateRelationType
)
