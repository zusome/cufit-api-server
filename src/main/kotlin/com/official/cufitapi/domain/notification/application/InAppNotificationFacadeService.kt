package com.official.cufitapi.domain.notification.application

import com.official.cufitapi.domain.notification.api.event.acl.InAppNotificationAntiCorruptionLayer
import org.springframework.stereotype.Service

@Service
class InAppNotificationFacadeService(
    private val inAppNotificationAntiCorruptionLayer: InAppNotificationAntiCorruptionLayer,
    private val registerInAppNotificationUseCase: RegisterInAppNotificationUseCase,
) {
}
