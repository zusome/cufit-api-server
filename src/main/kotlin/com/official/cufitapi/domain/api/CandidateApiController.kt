package com.official.cufitapi.domain.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@ApiV1Controller
class CandidateApiController {
// 추천순 (우리가 정한 score) , 최신순

    // 후보자 프로필 업데이트 API
    @PostMapping("/candidates")
    fun updateCandidateProfile() {

    }


}