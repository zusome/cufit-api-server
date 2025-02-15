package com.official.cufitapi.domain.member.domain

import com.official.cufitapi.domain.member.domain.vo.MemberType

class Member(
    val id: Long? = null,
    var name: String? = null,
    var email: String? = null,
    val memberType: MemberType
) {
    fun updateName(name: String) {
        this.name = name
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Member) return false
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}