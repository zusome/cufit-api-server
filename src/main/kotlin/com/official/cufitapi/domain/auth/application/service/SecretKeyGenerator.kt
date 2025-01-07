package com.official.cufitapi.domain.auth.application.service

import java.security.Key

@FunctionalInterface
fun interface SecretKeyGenerator {
    fun generate(secretKey: String): Key
}