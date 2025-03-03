package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.common.config.property.AuthorizationProperties
import com.official.cufitapi.domain.auth.application.command.AuthorizationTokenCreationCommand
import com.official.cufitapi.domain.auth.application.command.AuthorizationTokenRefreshCommand
import com.official.cufitapi.domain.auth.application.service.SecretKeyGenerator
import com.official.cufitapi.domain.auth.domain.AuthorizationToken
import com.official.cufitapi.domain.auth.domain.repository.AuthorizationTokenRepository
import com.official.cufitapi.domain.auth.domain.vo.AccessToken
import com.official.cufitapi.domain.auth.domain.vo.RefreshToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date

interface AuthorizationTokenRefreshUseCase {
    fun refresh(authorizationTokenRefreshCommand: AuthorizationTokenRefreshCommand): AuthorizationToken
}

interface AuthorizationTokenCreationUseCase {
    fun create(authorizationTokenCreationCommand: AuthorizationTokenCreationCommand): AuthorizationToken
}

interface AuthorizationTokenParsingUseCase {
    fun claims(accessToken: String): Claims
}

@Service
class AuthorizationTokenCreationService(
    private val authorizationProperties: AuthorizationProperties,
    private val secretKeyGenerator: SecretKeyGenerator,
    private val authorizationTokenRepository: AuthorizationTokenRepository
) : AuthorizationTokenCreationUseCase, AuthorizationTokenParsingUseCase, AuthorizationTokenRefreshUseCase {

    override fun create(command: AuthorizationTokenCreationCommand): AuthorizationToken {
        val secretKey = secretKeyGenerator.generate(authorizationProperties.secretKey)
        val memberId = command.memberId
        val accessToken = createAccessToken(secretKey, memberId, command.authority)
        val refreshToken = refreshToken(secretKey, memberId)
        return authorizationTokenRepository.save(AuthorizationToken(memberId, accessToken, refreshToken))
    }

    override fun refresh(command: AuthorizationTokenRefreshCommand): AuthorizationToken {
        val secretKey = secretKeyGenerator.generate(authorizationProperties.secretKey)
        val memberId = command.memberId
        val accessToken = AccessToken(command.accessToken)
        val authorizationToken = authorizationTokenRepository.findByMemberIdAndAccessToken(memberId, accessToken)
            ?: throw EntityNotFoundException()
        val renewAccessToken = createAccessToken(secretKey, memberId, command.authority)
        val refreshToken = refreshToken(secretKey, memberId)
        authorizationToken.refresh(renewAccessToken, refreshToken)
        return authorizationTokenRepository.save(authorizationToken)
    }

    private fun createAccessToken(
        secretKey: Key,
        memberId: Long,
        authority: String,
    ): AccessToken {
        val claims = Jwts.claims()
        claims.subject = memberId.toString()
        claims["authority"] = authority
        val accessToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + authorizationProperties.accessTimeOut))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
        return AccessToken(accessToken)
    }

    private fun refreshToken(
        secretKey: Key,
        memberId: Long,
    ): RefreshToken {
        val refreshClaims = Jwts.claims()
        refreshClaims.subject = memberId.toString() // 최소 정보만
        val refreshToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(refreshClaims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + authorizationProperties.refreshTimeOut))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
        return RefreshToken(refreshToken)
    }

    override fun claims(accessToken: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secretKeyGenerator.generate(authorizationProperties.secretKey))
            .build()
            .parseClaimsJws(accessToken)
            .body
            ?: throw IllegalArgumentException()
}
