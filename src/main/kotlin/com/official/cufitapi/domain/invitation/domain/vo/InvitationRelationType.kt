package com.official.cufitapi.domain.invitation.domain.vo

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException

enum class InvitationRelationType {
    FRIEND,
    FAMILY,
    COMPANION,
    ACQUAINTANCE
    ;

    fun suffix(): String =
        this.name.substring(0, 2)

    companion object {
        fun of(type: String): InvitationRelationType {
            return entries.firstOrNull { it.name == type }
                ?: throw InvalidRequestException(ErrorCode.INVALID_RELATION_TYPE)
        }
    }
}
