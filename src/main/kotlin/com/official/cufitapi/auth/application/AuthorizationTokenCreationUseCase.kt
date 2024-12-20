package com.official.cufitapi.auth.application

import com.official.cufitapi.auth.application.command.AccessTokenCreationCommand
import com.official.cufitapi.auth.domain.vo.AccessToken
import com.official.cufitapi.auth.domain.AuthorizationToken
import com.official.cufitapi.auth.domain.vo.RefreshToken
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

interface AuthorizationTokenCreationUseCase {
    fun create(accessTokenCreationCommand: AccessTokenCreationCommand): AuthorizationToken
}

@Service
class AuthorizationTokenCreationService: AuthorizationTokenCreationUseCase {
    
    override fun create(accessTokenCreationCommand: AccessTokenCreationCommand): AuthorizationToken {
        val accessToken = createAccessToken(accessTokenCreationCommand)
        val refreshToken = refreshToken(accessTokenCreationCommand)
        return AuthorizationToken(accessToken, refreshToken)
    }

    private fun refreshToken(accessTokenCreationCommand: AccessTokenCreationCommand): RefreshToken {
        val refreshClaims = Jwts.claims()
        refreshClaims.subject = accessTokenCreationCommand.memberId.toString() // 최소 정보만
        val refreshToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(refreshClaims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
            .signWith(secretKey(), SignatureAlgorithm.HS256)
            .compact()
        return RefreshToken(refreshToken)
    }

    private fun createAccessToken(accessTokenCreationCommand: AccessTokenCreationCommand): AccessToken {
        val claims = Jwts.claims()
        claims.subject = accessTokenCreationCommand.memberId.toString()
        claims["authority"] = accessTokenCreationCommand.authority.name
        val accessToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
            .signWith(secretKey(), SignatureAlgorithm.HS256)
            .compact()
        return AccessToken(accessToken)
    }

    private fun secretKey(): SecretKey? = Keys.hmacShaKeyFor(SECRET.toByteArray())

    companion object {
        private const val ACCESS_TOKEN_EXPIRATION: Long = 1000 * 60 * 60 * 24 // 1 day
        private const val REFRESH_TOKEN_EXPIRATION: Long = 1000 * 60 * 60 * 24 * 7 // 3 day
        private const val SECRET: String = ""
    }
}