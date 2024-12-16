package com.official.cufitapi.domain.infrastructure.repository.dto.candidate

import com.official.cufitapi.domain.enums.MBTILetter
import com.official.cufitapi.domain.infrastructure.repository.MatchCandidateJpaRepository.Image

data class CandidateInfo(
    val candidateId: Long,
    val images: List<Image>,
    val name: String,
    val age: String,
    val relation: String,
    val matchMakerName: String,
    val mbti: List<MBTILetter>,
    val height: Int,
    val station: String,
    val job: String
)
