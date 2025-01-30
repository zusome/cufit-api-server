package com.official.cufitapi.domain.member.api.dto

import com.official.cufitapi.domain.member.application.command.UpdateMemberProfileCommand
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "사용자 실명 정보 작성 요청")
data class UpdateMemberProfileRequest(
    @Schema(description = "이름", example = "홍길동")
    val name: String,
) {
    fun toCommand(memberId: Long): UpdateMemberProfileCommand =
        UpdateMemberProfileCommand(
            memberId = memberId,
            name = name,
        )
}
