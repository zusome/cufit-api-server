package com.official.cufitapi.domain.notification.infrastructure.client.cipher

import com.official.cufitapi.common.config.property.SensProperties
import org.springframework.stereotype.Component
import java.io.UnsupportedEncodingException
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

// @Component
class SensCipher(
    sensProperties: SensProperties,
) {
    private val signingKey = SecretKeySpec(sensProperties.secretKey.toByteArray(charset(UTF_8)), HMAC_SHA_256)
    private val mac: Mac = Mac.getInstance(HMAC_SHA_256).also { it.init(signingKey) }

    fun encode(message: String): String {
        try {
            val raw = mac.doFinal(message.toByteArray(charset(UTF_8)))
            return Base64.getEncoder().encodeToString(raw)
        } catch (e: UnsupportedEncodingException) {
            return e.toString()
        }
    }

    companion object {
        private const val HMAC_SHA_256 = "HmacSHA256"
        private const val UTF_8 = "UTF-8"
    }
}
