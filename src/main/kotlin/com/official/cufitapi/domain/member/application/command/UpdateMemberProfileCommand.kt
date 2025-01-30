package com.official.cufitapi.domain.member.application.command

data class UpdateMemberProfileCommand(
    val memberId: Long,
    val name: String,
) {
}
