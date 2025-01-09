package com.official.cufitapi.domain.invitation.api.dto

import io.swagger.v3.oas.annotations.media.Schema


@Schema(name = "초대코드 요청")
data class InvitationCodeRequest(
    @Schema(description = "초대코드", example = "CA1234567A")
    val invitationCode: String
) {
    init {
        // if (invitationCode.length != 10) {
        //     throw InvalidRequestException("초대코드는 10글자 입니다.")
        // }
    }
}