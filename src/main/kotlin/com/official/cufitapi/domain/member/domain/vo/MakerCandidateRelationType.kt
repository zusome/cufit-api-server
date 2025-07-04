package com.official.cufitapi.domain.member.domain.vo

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException

enum class MakerCandidateRelationType {
    FRIEND,
    FAMILY,
    COMPANION,
    ACQUAINTANCE;

    companion object {
        @JvmStatic
        fun invitationCodeSuffix(relationType: MakerCandidateRelationType) = when (relationType) {
            FRIEND -> "FR"
            FAMILY -> "FA"
            COMPANION -> "CO"
            ACQUAINTANCE -> "AC"
        }

        @JvmStatic
        fun fromInvitationCodeSuffix(suffix: String) = when (suffix) {
            "FR" -> FRIEND
            "FA" -> FAMILY
            "CO" -> COMPANION
            "AC" -> ACQUAINTANCE
            else -> {
                throw InvalidRequestException(ErrorCode.INVALID_SUFFIX)
            }
        }

        fun of(type: String): MakerCandidateRelationType {
            return entries.firstOrNull { it.name == type }
                ?: throw InvalidRequestException(ErrorCode.INVALID_RELATION_TYPE)
        }
    }
}
