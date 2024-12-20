package com.official.cufitapi.auth.application.service

import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key

@Component
class HmacShaKeyGenerator : SecretKeyGenerator {

    override fun generate(secretKey: String): Key {
        return Keys.hmacShaKeyFor(secretKey.toByteArray())
    }
}