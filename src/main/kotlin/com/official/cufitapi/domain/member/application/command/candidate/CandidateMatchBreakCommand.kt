package com.official.cufitapi.domain.member.application.command.candidate

data class CandidateMatchBreakCommand(
    val memberId: Long,
    val isMatchPaused: Boolean
)
