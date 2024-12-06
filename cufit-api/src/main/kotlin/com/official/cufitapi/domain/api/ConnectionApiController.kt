package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.application.ConnectionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@ApiV1Controller
class ConnectionApiController(
    private val connectionService: ConnectionService
) {

    // 연결 정보확인
    @GetMapping("/connections")
    fun getConnection(
        memberId: Long
    ) {

    }

    // 연결 요청
    @PostMapping("/connections")
    fun applyConnection(
        memberId: Long
    ) {

    }

    // 연결 수락
    @PostMapping("/connections/approval")
    fun approveConnection(
        memberId: Long
    ) {

    }

    // 연결 거절
    @PostMapping("/connections/rejection")
    fun rejectConnection(
        memberId: Long
    ) {

    }

}