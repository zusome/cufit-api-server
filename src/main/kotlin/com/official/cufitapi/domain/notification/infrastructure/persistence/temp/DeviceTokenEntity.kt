package com.official.cufitapi.domain.notification.infrastructure.persistence.temp

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "device_token")
@Entity
class DeviceTokenEntity(

    @Column(name = "member_id")
    var memberId: Long,

    @Column(name = "platform")
    var platform: String,

    @Column(name = "device_token")
    var deviceToken: String,

    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: LocalDateTime,

    @Column(name = "updated_at", updatable = true, nullable = false)
    var updatedAt: LocalDateTime,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
