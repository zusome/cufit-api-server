package com.official.cufitapi.domain.notification.infrastructure.persistence

import com.official.cufitapi.domain.notification.infrastructure.persistence.JDBCNotificationDao.FindAllNotifications

fun interface NotificationDao {
    fun findAll(userId: Long): List<FindAllNotifications>
}
