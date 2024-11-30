package com.official.cufitapi.domain.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@ApiV1Controller
class ConnectionApiController {

    // 연결 정보확인
    @GetMapping("/connections")
    fun getConnection() {

    }

    // 연결 요청
    @PostMapping("/connections")
    fun applyConnection() {

    }

    // 연결 수락
    @PostMapping("/connections/approve")
    fun approveConnection() {

    }

}