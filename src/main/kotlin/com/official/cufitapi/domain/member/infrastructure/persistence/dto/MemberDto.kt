package com.official.cufitapi.domain.member.infrastructure.persistence.dto

data class MemberDto(
    var name: String,
    var email: String? = null,
    var id: Long
)

