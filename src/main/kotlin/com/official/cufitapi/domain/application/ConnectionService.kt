package com.official.cufitapi.domain.application

import com.official.cufitapi.domain.api.dto.connection.ConnectionApplyRequest
import com.official.cufitapi.domain.infrastructure.entity.MatchConnection
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConnectionService {

    fun apply(memberId: Long, request: ConnectionApplyRequest) {
        // 요청 정보 DB에 저장
//        val connection = MatchConnection()
        // TODO : 알림
    }

    fun reject() {
        // 연결 요청 제거
        // push
    }
}