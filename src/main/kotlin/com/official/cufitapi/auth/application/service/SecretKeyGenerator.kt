package com.official.cufitapi.auth.application.service

import java.security.Key

interface SecretKeyGenerator {
    fun generate(secretKey: String): Key
}