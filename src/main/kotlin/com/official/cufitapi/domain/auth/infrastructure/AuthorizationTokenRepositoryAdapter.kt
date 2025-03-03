package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.domain.auth.domain.AuthorizationToken
import com.official.cufitapi.domain.auth.domain.repository.AuthorizationTokenRepository
import com.official.cufitapi.domain.auth.domain.vo.AccessToken
import com.official.cufitapi.domain.auth.domain.vo.RefreshToken
import com.official.cufitapi.domain.auth.infrastructure.persist.JpaAuthorizationToken
import com.official.cufitapi.domain.auth.infrastructure.persist.JpaAuthorizationTokenRepository
import org.springframework.stereotype.Component

@Component
class AuthorizationTokenRepositoryAdapter(
    private val repository: JpaAuthorizationTokenRepository,
) : AuthorizationTokenRepository {

    override fun save(authorizationToken: AuthorizationToken): AuthorizationToken {
        return toDomain(repository.save(toEntity(authorizationToken)))
    }

    override fun findByMemberId(memberId: Long): AuthorizationToken? {
        return repository.findByMemberId(memberId)
            ?.let { toDomain(it) }
    }

    private fun toEntity(domain: AuthorizationToken) =
        JpaAuthorizationToken(
            memberId = domain.memberId,
            accessToken = domain.accessToken.accessToken,
            refreshToken = domain.refreshToken.refreshToken
        )

    private fun toDomain(entity: JpaAuthorizationToken) =
        AuthorizationToken(
            entity.memberId,
            AccessToken(entity.accessToken),
            RefreshToken(entity.refreshToken)
        )
}
