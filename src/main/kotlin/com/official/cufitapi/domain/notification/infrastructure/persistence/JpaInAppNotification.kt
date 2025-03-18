package com.official.cufitapi.domain.notification.infrastructure.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "in_app_notifications")
@Entity
class JpaInAppNotification(

    var title: String,
    var content: String,
    var receiverId: Long,
    var notificationType: String,
    var payload: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}
