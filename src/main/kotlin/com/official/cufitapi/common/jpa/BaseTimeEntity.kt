package com.official.cufitapi.common.jpa

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseTimeEntity(
    @CreatedDate
    @Column(updatable = false)
    var createdDate: LocalDateTime? = LocalDateTime.now(), // 기본값 설정
    @LastModifiedDate
    var modifiedDate: LocalDateTime? = LocalDateTime.now()
)
