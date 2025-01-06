package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.ConnectionApiDocs
import com.official.cufitapi.domain.member.api.dto.connection.ConnectionApplyRequest
import com.official.cufitapi.domain.member.api.dto.connection.ConnectionUpdateRequest
import com.official.cufitapi.domain.member.application.ConnectionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class ConnectionApi(
    private val connectionService: ConnectionService
) : ConnectionApiDocs {

    // 연결 정보확인
    @GetMapping("/connections")
    fun getConnectionList(
        @Authorization(AuthorizationType.ALL) memberId: Long
    ) {
    }

    // 연결 요청
    // - 본인의 후보자 중 1명을 선택해서 연결 요청을 보낼 수 있음.
    //    - 후보자 1명당 1일 최대 3명까지 연결 요청을 보낼 수 있음.
    // 같은 성별에게는 연결 요청을 보낼 수 없음. (생물학적 성별을 기준으로 함)
    // 12시간 이내로 둘 다 의사를 표현하지 않으면 연결 요청이 자동으로 종료됨. -> Cache
    @PostMapping("/connections")
    fun applyConnection(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: ConnectionApplyRequest
    ) {
        connectionService.apply(memberId, request)
    }

    // 연결 수락
    @PatchMapping("/connections/{connectionId}")
    fun updateConnection(
        @PathVariable connectionId: Long,
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: ConnectionUpdateRequest
    ) {
        connectionService.updateConnectionStatus(connectionId, request)
    }

    @GetMapping("/connections/received")
    fun getReceivedConnections(
        @Authorization(AuthorizationType.ALL) memberId: Long
    ): HttpResponse<Unit> {
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }

    @GetMapping("/connections/result")
    fun getConnectionsResults(
        @Authorization(AuthorizationType.ALL) memberId: Long
    ): HttpResponse<Unit> {
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }
}