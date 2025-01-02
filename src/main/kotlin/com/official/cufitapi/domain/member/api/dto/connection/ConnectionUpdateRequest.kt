package com.official.cufitapi.domain.member.api.dto.connection

import com.official.cufitapi.domain.member.enums.MatchStatus

data class ConnectionUpdateRequest(
    val matchStatus: MatchStatus
)