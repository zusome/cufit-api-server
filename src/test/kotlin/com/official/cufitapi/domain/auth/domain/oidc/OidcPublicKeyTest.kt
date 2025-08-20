package com.official.cufitapi.domain.auth.domain.oidc

import com.official.cufitapi.domain.auth.domain.oidc.vo.OidcPublicKeyId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OidcPublicKeyTest {

    @Test
    fun `kid 와 alg 가 같으면 True 를 반환한다`() {
        val oidcPublicKey =
            OidcPublicKey(kid = "test-kid", alg = "RS256", kty = "RSA", use = "sig", n = "test-n", e = "AQAB")
        val matchingKeyId = OidcPublicKeyId(kid = "test-kid", alg = "RS256")

        val actual = oidcPublicKey.match(matchingKeyId)

        assertThat(actual).isTrue()
    }

    @Test
    fun `kid 가 다르면 False 를 반환한다`() {
        val oidcPublicKey =
            OidcPublicKey(kid = "test-kid", alg = "RS256", kty = "RSA", use = "sig", n = "test-n", e = "AQAB")
        val nonMatchingKeyId = OidcPublicKeyId(kid = "different-kid", alg = "RS256")

        val actual = oidcPublicKey.match(nonMatchingKeyId)

        assertThat(actual).isFalse()
    }

    @Test
    fun `alg 가 다르면 False 를 반환한다`() {
        val oidcPublicKey =
            OidcPublicKey(kid = "test-kid", alg = "RS256", kty = "RSA", use = "sig", n = "test-n", e = "AQAB")
        val nonMatchingKeyId = OidcPublicKeyId(kid = "test-kid", alg = "ES256")

        val actual = oidcPublicKey.match(nonMatchingKeyId)

        assertThat(actual).isFalse()
    }
}
