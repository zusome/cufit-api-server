package com.official.cufitapi.domain.match.infrastructure.persistence

enum class MatchStatus(
    val step: Int
) {
    SUGGESTED(0),
    ACCEPTED(1),
    MATCHED(2),
    REJECTED(3),
    CANCELED(4) // 24시간 정책으로 인해 취소된 케이스를 의미한다.
    ;

    fun nextStatus(): MatchStatus {
        return when (this) {
            SUGGESTED -> ACCEPTED
            ACCEPTED -> MATCHED
            else -> this
        }
    }

    fun hasNext(): Boolean {
        return this.step < MATCHED.step
    }

    companion object {
        fun of(matchStatus: Int): MatchStatus {
            return entries.firstOrNull { it.step == matchStatus }
                ?: throw IllegalArgumentException("Invalid match status")
        }
    }
}
