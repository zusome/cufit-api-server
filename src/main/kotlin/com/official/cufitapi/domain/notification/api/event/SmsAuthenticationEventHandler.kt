package com.official.cufitapi.domain.notification.api.event

import com.official.cufitapi.domain.auth.domain.sms.event.RegisterSmsAuthenticationEvent
import com.official.cufitapi.domain.notification.application.SendMessageUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SmsAuthenticationEventHandler(
    private val sendMessageUseCase: SendMessageUseCase,
) {

    @EventListener
    fun handleMessageEvent(event: RegisterSmsAuthenticationEvent) {

    }
}
