package com.official.cufitapi.domain.notification.domain.message

data class MessageDetails(
    val values: List<MessageDetail>
) {
    init {
        if(values.size > 100) {
            throw IllegalArgumentException("Messages size exceeds 1000.")
        }
    }
}
