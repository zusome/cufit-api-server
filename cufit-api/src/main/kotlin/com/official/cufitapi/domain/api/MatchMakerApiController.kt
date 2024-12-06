package com.official.cufitapi.domain.api

import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class MatchMakerApiController {

    // 본인이 등록한 후보자 목록 조회
    @GetMapping("/matchmakers")
    fun getCandidates() {

    }
}