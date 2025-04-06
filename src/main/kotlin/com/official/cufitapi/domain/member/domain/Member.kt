package com.official.cufitapi.domain.member.domain

import com.official.cufitapi.domain.member.domain.vo.MemberType

class Member(
    val id: Long? = null,
    var email: String? = null,
    var name: String? = null,
    var memberAuthorization: MemberAuthorization,
    var memberType: MemberType
) {
    fun updateName(name: String) {
        this.name = name
    }

    fun updateMaker() {
        if(this.memberType != MemberType.BASIC) {
            throw IllegalStateException("MemberType is not BASIC")
        }
        this.memberType = MemberType.MAKER
    }

    fun updateCandidate() {
        if(this.memberType != MemberType.BASIC) {
            throw IllegalStateException("MemberType is not BASIC")
        }
        this.memberType = MemberType.CANDIDATE
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
