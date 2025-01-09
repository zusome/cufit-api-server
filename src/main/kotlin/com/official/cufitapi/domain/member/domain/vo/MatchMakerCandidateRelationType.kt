package com.official.cufitapi.domain.member.domain.vo

import com.official.cufitapi.common.exception.InvalidRequestException

enum class MatchMakerCandidateRelationType {
    FRIEND,
    FAMILY,
    COMPANION,
    ACQUAINTANCE;

    companion object {
        @JvmStatic
        fun invitationCodeSuffix(relationType: MatchMakerCandidateRelationType) = when (relationType) {
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
                throw InvalidRequestException("잘못된 suffix")
            }
        }
    }
}