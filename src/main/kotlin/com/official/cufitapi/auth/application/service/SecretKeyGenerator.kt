package com.official.cufitapi.auth.application.service

import java.security.Key

@FunctionalInterface
fun interface SecretKeyGenerator {
    fun generate(secretKey: String): Key
}