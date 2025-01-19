package com.official.cufitapi.domain.member.api.dto.candidate

import com.official.cufitapi.domain.member.domain.vo.MBTILetter
import io.swagger.v3.oas.annotations.media.Schema

data class CandidateResponse(
    val candidateId: Long,
    val images: List<Image>,
    val name: String,
    val age: String,
    @Schema(description = "주선자와의 관계", example = "직장동료")
    val relation: String,
    val matchMakerName: String,
    val mbti: List<MBTILetter>,
    val height: Int,
    val station: String,
    val job: String
)

data class Image(
    val imageUrl: String,
    val profileOrder: Int
) {

}
