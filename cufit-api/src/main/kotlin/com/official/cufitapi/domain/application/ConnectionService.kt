package com.official.cufitapi.domain.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConnectionService {

    fun reject() {
        // 연결 요청 제거
        // push
    }
}