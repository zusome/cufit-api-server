package com.official.cufitapi.domain.enums

enum class MatchStatus {
    PROGRESSING,
    REJECTED,
    ACCEPTED;

    companion object {
        @JvmStatic
        fun matchResults(): List<MatchStatus> {
            return listOf(ACCEPTED, REJECTED, PROGRESSING)
        }

        @JvmStatic
        fun received(): List<MatchStatus> {
            return listOf(PROGRESSING)
        }
    }
}