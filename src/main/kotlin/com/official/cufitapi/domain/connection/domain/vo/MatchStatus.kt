package com.official.cufitapi.domain.connection.domain.vo

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

        fun of(matchStatus: String): MatchStatus =
            entries.firstOrNull { it.name == matchStatus }
                ?: throw IllegalArgumentException("Unknown match status: $matchStatus")
    }
}