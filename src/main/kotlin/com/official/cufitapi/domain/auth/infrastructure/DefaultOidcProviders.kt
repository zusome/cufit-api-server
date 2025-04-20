package com.official.cufitapi.domain.auth.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.official.cufitapi.domain.auth.domain.oidc.OidcPublicKey
import com.official.cufitapi.domain.auth.domain.oidc.OidcPublicKeys
import com.official.cufitapi.domain.auth.domain.oidc.OidcProviders
import com.official.cufitapi.domain.auth.domain.oidc.vo.OidcPublicKeyId
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

@Component
class DefaultOidcProviders(
    restClientBuilder: RestClient.Builder,
) : OidcProviders {

    private val appleClient: RestClient = restClientBuilder
        .baseUrl("https://appleid.apple.com")
        .build()

    override fun findByIdToken(idToken: String, provider: String): String {
        val oidcPublicKeyId = oidcPublicKeyId(idToken)
        val oidcPublicKeys = oidcPublicKeys()
        val oidcPublicKey = oidcPublicKeys.match(oidcPublicKeyId)
        val publicKey = publicKey(oidcPublicKey)
        val parseClaims = parseClaims(idToken, publicKey)
        return parseClaims.subject
    }

    private fun oidcPublicKeys() = appleClient.get()
        .uri("/auth/keys")
        .retrieve()
        .toEntity(OidcPublicKeys::class.java)
        .body ?: throw RuntimeException()

    fun parseClaims(token: String, publicKey: PublicKey): Claims =
        Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token)
            .body

    private fun publicKey(publicKey: OidcPublicKey): PublicKey {
        val nBytes = Base64.getUrlDecoder().decode(publicKey.n)
        val eBytes = Base64.getUrlDecoder().decode(publicKey.e)
        val publicKeySpec = RSAPublicKeySpec(BigInteger(1, nBytes), BigInteger(1, eBytes))
        return KeyFactory.getInstance(publicKey.kty).generatePublic(publicKeySpec)
    }

    fun oidcPublicKeyId(token: String): OidcPublicKeyId {
        val headerString = Base64.getUrlDecoder().decode(token.split(".")[0])
        val headers = jacksonObjectMapper().readValue(headerString, MutableMap::class.java) as Map<String, String>
        return OidcPublicKeyId(
            headers["kid"] ?: throw RuntimeException(),
            headers["alg"] ?: throw RuntimeException()
        )
    }
}
