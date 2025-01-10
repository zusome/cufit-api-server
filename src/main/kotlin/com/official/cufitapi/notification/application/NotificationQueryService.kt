package com.official.cufitapi.notification.application

import com.official.cufitapi.notification.domain.Notification
import com.official.cufitapi.notification.domain.NotificationRepository
import org.springframework.stereotype.Service


@Service
class NotificationQueryService(
    private val notificationRepository: NotificationRepository
) : NotificationGetUseCase {
    override fun findAll(memberId : Long) : List<Notification> {
        return notificationRepository.findAll(memberId)
    }


}