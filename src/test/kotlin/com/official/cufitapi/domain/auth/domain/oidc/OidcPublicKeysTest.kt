package com.official.cufitapi.domain.auth.domain.oidc

import com.official.cufitapi.domain.auth.domain.oidc.vo.OidcPublicKeyId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OidcPublicKeysTest {

    @Test
    fun `kid 와 alg 가 일치하는 OidcPublicKey 를 반환한다`() {
        // given
        val key1 = OidcPublicKey("kid1", "RS256", "RSA", "sig", "n1", "AQAB")
        val key2 = OidcPublicKey("kid2", "RS256", "RSA", "sig", "n2", "AQAB")
        val key3 = OidcPublicKey("kid3", "ES256", "EC", "sig", "n3", "AQAB")
        val oidcPublicKeys = OidcPublicKeys(listOf(key1, key2, key3))

        val searchKeyId = OidcPublicKeyId("kid2", "RS256")
        val actual = oidcPublicKeys.findByOidcPublicKeyId(searchKeyId)

        assertThat(actual).isEqualTo(key2)
    }

    @Test
    fun `kid 와 alg 가 일치하는 OidcPublicKey 가 없을 경우 에러를 발생한다`() {
        // given
        val key1 = OidcPublicKey("kid1", "RS256", "RSA", "sig", "n1", "AQAB")
        val key2 = OidcPublicKey("kid2", "RS256", "RSA", "sig", "n2", "AQAB")

        val oidcPublicKeys = OidcPublicKeys(listOf(key1, key2))
        val nonExistentKeyId = OidcPublicKeyId("kid3", "RS256")

        assertThrows<RuntimeException> {
            oidcPublicKeys.findByOidcPublicKeyId(nonExistentKeyId)
        }
    }

    @Test
    fun `OidcPublicKeys 가 비어있을 때 키를 조회하면 에러를 발생한다`() {
        // given
        val emptyOidcPublicKeys = OidcPublicKeys(emptyList())
        val searchKeyId = OidcPublicKeyId("any-kid", "RS256")

        // when & then
        assertThrows<RuntimeException> {
            emptyOidcPublicKeys.findByOidcPublicKeyId(searchKeyId)
        }
    }
}
