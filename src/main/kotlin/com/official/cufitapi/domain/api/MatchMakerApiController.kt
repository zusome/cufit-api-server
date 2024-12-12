package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.application.MatchMakerService
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class MatchMakerApiController(
    private val matchMakerService: MatchMakerService
) {

    // 본인이 등록한 후보자 목록 조회
    @GetMapping("/matchmakers/candidates")
    fun getCandidates(
        matchMakerId: Long
    ) {
        // matchMakerService.getCandidates(matchMakerId)
    }
}