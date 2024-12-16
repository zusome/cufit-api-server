package com.official.cufitapi.domain.infrastructure.repository.dto.candidate

import com.official.cufitapi.domain.enums.MBTILetter

data class CandidateInfo(
    val candidateId: Long,
    val name: String,
    val age: String,
    val relation: String,
    val matchMakerName: String,
    val mbti: List<MBTILetter>,
    val height: Int,
    val station: String,
    val job: String
)