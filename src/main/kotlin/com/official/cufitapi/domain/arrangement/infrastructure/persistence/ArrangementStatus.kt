package com.official.cufitapi.domain.arrangement.infrastructure.persistence

enum class ArrangementStatus(
    private val step: Int
) {
    SUGGESTED(0),
    ACCEPTED(1),
    MATCHED(2),
    REJECTED(3)
    ;

    fun nextStatus(): ArrangementStatus {
        return when (this) {
            SUGGESTED -> ACCEPTED
            ACCEPTED -> MATCHED
            else -> this
        }
    }
}