package com.official.cufitapi.domain.api.dto.connection

import com.official.cufitapi.domain.enums.MatchStatus

data class ConnectionUpdateRequest(
    val matchStatus: MatchStatus
)