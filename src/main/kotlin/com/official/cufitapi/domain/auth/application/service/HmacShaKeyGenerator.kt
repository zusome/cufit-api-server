package com.official.cufitapi.domain.auth.application.service

import com.official.cufitapi.domain.auth.domain.oidc.SecretKeyGenerator
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key

@Component
class HmacShaKeyGenerator : SecretKeyGenerator {

    override fun generate(secretKey: String): Key {
        return Keys.hmacShaKeyFor(secretKey.toByteArray())
    }
}
