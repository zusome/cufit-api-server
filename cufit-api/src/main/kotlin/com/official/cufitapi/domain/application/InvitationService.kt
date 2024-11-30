package com.official.cufitapi.domain.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class InvitationService {

    companion object {
        private const val BASE_62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    }

    fun generateInvitationCode() {

    }

    private fun generateRandomBase62String(length: Int = 8): String {
        return (1..length)
            .map { Random.nextInt(0, BASE_62_CHARS.length) }
            .map(BASE_62_CHARS::get)
            .joinToString("")
    }
}