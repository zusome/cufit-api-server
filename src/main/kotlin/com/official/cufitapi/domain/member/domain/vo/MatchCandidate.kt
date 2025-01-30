package com.official.cufitapi.domain.member.domain.vo

class MatchCandidate(
    val id: Long,
    val name: String,
    val age: Int,
    val relation: String,
    val matchMakerName: String,
    val mbti: String,
    val height: Int,
    val station: String,
    val job: String
) {
}