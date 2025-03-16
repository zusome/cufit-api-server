package com.official.cufitapi.domain.invitation.domain.factory

import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode
import com.official.cufitapi.domain.invitation.domain.vo.InvitationRelationType
import com.official.cufitapi.domain.invitation.domain.vo.InvitationType
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class DefaultInvitationCodeFactory : InvitationCodeFactory {

    override fun generate(
        invitationType: InvitationType,
        invitationRelationType: InvitationRelationType,
    ): InvitationCode {
        val invitationCodePrefix = invitationType.suffix()
        val invitationCode = generateRandomBase62String()
        val invitationCodeSuffix = invitationRelationType.suffix()
        return InvitationCode("${invitationCodePrefix}$invitationCode${invitationCodeSuffix}")
    }

    private fun generateRandomBase62String(length: Int = 8): String {
        return (1..length)
            .map { Random.nextInt(0, BASE_62_CHARS.length) }
            .map(BASE_62_CHARS::get)
            .joinToString("")
    }

    companion object {
        private const val BASE_62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    }
}
