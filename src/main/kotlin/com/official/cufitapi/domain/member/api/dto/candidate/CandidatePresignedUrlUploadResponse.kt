package com.official.cufitapi.domain.member.api.dto.candidate

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "후보자 프로필 업로드 응답")
data class CandidatePresignedUrlUploadResponse(
    @Schema(description = "후보자 프로필 업로드 presigned url", example = "https://example.com")
    val presignedUrl: String?,
)
