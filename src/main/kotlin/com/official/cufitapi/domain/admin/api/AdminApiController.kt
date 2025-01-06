package com.official.cufitapi.domain.admin.api

import com.official.cufitapi.domain.admin.api.docs.AdminApiV1Controller
import org.springframework.web.bind.annotation.PostMapping

@AdminApiV1Controller
class AdminApiController {


    // 후보자 등록 (어드민)
    @PostMapping
    fun registerCandidate(
    ) {
        // TODO : implementation
    }
}