package com.official.cufitapi.domain.api.dto.candidate

import com.official.cufitapi.domain.enums.MBTILetter
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "후보자 응답")
data class CandidateResponse(
    @Schema(description = "후보자 ID", example = "1")
    val candidateId: Long,
    @Schema(description = "")
    val images: List<Image>,
    @Schema(description = "이름", example = "홍길동")
    val name: String,
    @Schema(description = "나이", example = "33")
    val age: String,
    @Schema(description = "주선자와의 관계", example = "직장동료")
    val relation: String,
    @Schema(description = "주선자 이름", example = "김철수")
    val matchMakerName: String,
    val mbti: List<MBTILetter>,
    val height: Int,
    val station: String,
    val job: String
)

data class Image(
    val imageUrl: String ,
    val profileOrder: Int
) {

}
