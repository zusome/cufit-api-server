package com.official.cufitapi.domain.member.api.dto.connection

import com.official.cufitapi.domain.member.enums.IdealHeightUnit
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "받은 매칭 신청 목록 응답")
data class ReceivedConnectionResponse(
    @Schema(description = "후보자 ID", example = "10")
    val candidateId: Long,
    @Schema(description = "키", example = "142")
    val height: Int,
    @Schema(description = "나이 이상형")
    val idealAge: List<com.official.cufitapi.domain.member.enums.IdealAge>,
    @Schema(description = "키 이상형")
    val idealHeight: List<IdealHeightUnit>,
    @Schema(description = "프로필 이미지")
    val profileImages: List<ImageResponse>,
)

@Schema(name = "이미지 응답")
data class ImageResponse(
    @Schema(description = "이미지 URL", example = "https://example.com/image.jpg")
    val imageUrl: String,
    @Schema(description = "프로필 이미지 순서")
    val profileOrder: Int
) {
}
