package com.official.cufitapi.domain.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@ApiV1Controller
class CandidateApiController {


    // 후보자 정보 조회
    @GetMapping("/candidates")
    fun getCandidates() {

    }


    // 후보자 프로필 업데이트 API
    @PostMapping("/candidates")
    fun updateCandidateProfile() {

    }


}