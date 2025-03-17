package com.official.cufitapi.domain.member.application.command

data class UpdateMemberRealNameCommand(
    val memberId: Long,
    val name: String,
) {
}
