package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.config.property.AuthorizationProperties
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.common.exception.UnAuthorizedException
import com.official.cufitapi.domain.auth.application.command.CreateAuthorizationTokenCommand
import com.official.cufitapi.domain.auth.application.command.RefreshAuthorizationTokenCommand
import com.official.cufitapi.domain.auth.domain.oidc.SecretKeyGenerator
import com.official.cufitapi.domain.auth.domain.token.AuthorizationToken
import com.official.cufitapi.domain.auth.domain.token.AuthorizationTokenRepository
import com.official.cufitapi.domain.auth.domain.token.vo.AccessToken
import com.official.cufitapi.domain.auth.domain.token.vo.RefreshToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date

interface AuthorizationTokenRefreshUseCase {
    fun refresh(refreshAuthorizationTokenCommand: RefreshAuthorizationTokenCommand): AuthorizationToken
}

interface AuthorizationTokenCreationUseCase {
    fun create(createAuthorizationTokenCommand: CreateAuthorizationTokenCommand): AuthorizationToken
}

interface AuthorizationTokenParsingUseCase {
    fun claims(accessToken: String): Claims
}

@Service
class AuthorizationTokenService(
    private val authorizationProperties: AuthorizationProperties,
    private val secretKeyGenerator: SecretKeyGenerator,
    private val authorizationTokenRepository: AuthorizationTokenRepository
) : AuthorizationTokenCreationUseCase, AuthorizationTokenParsingUseCase, AuthorizationTokenRefreshUseCase {

    override fun create(command: CreateAuthorizationTokenCommand): AuthorizationToken {
        val secretKey = secretKeyGenerator.generate(authorizationProperties.secretKey)
        val memberId = command.memberId
        val accessToken = createAccessToken(secretKey, memberId, command.authority)
        val refreshToken = refreshToken(secretKey, memberId)
        return authorizationTokenRepository.save(AuthorizationToken(memberId, accessToken, refreshToken))
    }

    override fun refresh(command: RefreshAuthorizationTokenCommand): AuthorizationToken {
        val secretKey = secretKeyGenerator.generate(authorizationProperties.secretKey)
        val memberId = command.memberId
        val authorizationToken = authorizationTokenRepository.findByMemberId(memberId)
            ?: throw NotFoundException(ErrorCode.INVALID_ACCESS_TOKEN)
        val refreshToken = authorizationToken.refreshToken
        if(refreshToken.isNotSame(command.refreshToken)) {
            throw NotFoundException(ErrorCode.INVALID_REFRESH_TOKEN)
        }
        verifyRefreshTokenIsExpired(refreshToken)
        val renewAccessToken = createAccessToken(secretKey, memberId, command.authority.name)
        val renewRefreshToken = refreshToken(secretKey, memberId)
        authorizationToken.refresh(renewAccessToken, renewRefreshToken)
        return authorizationTokenRepository.save(authorizationToken)
    }

    private fun verifyRefreshTokenIsExpired(refreshToken: RefreshToken) {
        val claims = Jwts.parserBuilder()
            .setSigningKey(secretKeyGenerator.generate(authorizationProperties.secretKey))
            .build()
            .parseClaimsJws(refreshToken.refreshToken)
            .body
        if (claims.expiration.before(Date())) {
            throw UnAuthorizedException(ErrorCode.EXPIRED_REFRESH_TOKEN)
        }
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

    override fun claims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKeyGenerator.generate(authorizationProperties.secretKey))
                .build()
                .parseClaimsJws(accessToken)
                .body
                ?: throw UnAuthorizedException(ErrorCode.INVALID_ACCESS_TOKEN)
        } catch (e: ExpiredJwtException) {
            throw UnAuthorizedException(ErrorCode.EXPIRED_ACCESS_TOKEN)
        }
    }
}
