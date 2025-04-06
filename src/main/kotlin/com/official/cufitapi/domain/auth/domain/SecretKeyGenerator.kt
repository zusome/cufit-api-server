package com.official.cufitapi.domain.auth.domain

import java.security.Key

@FunctionalInterface
fun interface SecretKeyGenerator {
    fun generate(secretKey: String): Key
}
