package com.official.cufitapi.domain.member.application.command

import com.official.cufitapi.domain.member.domain.Member
import com.official.cufitapi.domain.member.domain.MemberAuthorization
import com.official.cufitapi.domain.member.domain.vo.MemberType

data class RegisterMemberCommand(
    val name: String?,
    val email: String?,
    val provider: String,
    val providerId: String,
    val authority: String,
) {
    fun init(): Member = Member(
        name = this.name,
        email = this.email,
        memberAuthorization = MemberAuthorization(
            provider = this.provider,
            providerId = this.providerId,
        ),
        memberType = MemberType.ofName(this.authority)
    )
}
