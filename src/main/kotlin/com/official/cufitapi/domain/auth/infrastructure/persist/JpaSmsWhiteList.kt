package com.official.cufitapi.domain.auth.infrastructure.persist

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Table(name = "sms_white_list")
@Entity
class JpaSmsWhiteList(
    @Comment("전화번호")
    @Column(name = "phone", unique = true, nullable = false, updatable = false)
    val phone: String,

    @Id @GeneratedValue
    val id: Long,
)
