package com.official.cufitapi.domain.api.dto

import com.official.cufitapi.common.exception.InvalidRequestException
import io.swagger.v3.oas.annotations.media.Schema


@Schema(name = "초대코드 요청")
data class InvitationCodeRequest(
    @Schema(description = "초대코드", example = "1234567A")
    val invitationCode: String
) {
    init {
        if (invitationCode.length != 8) {
            throw InvalidRequestException("초대코드는 8글자 입니다.")
        }
    }
}