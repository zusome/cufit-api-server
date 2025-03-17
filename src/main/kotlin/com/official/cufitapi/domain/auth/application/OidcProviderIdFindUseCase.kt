package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.OidcProviderIdFindCommand
import com.official.cufitapi.domain.auth.domain.repository.OidcProviders
import org.springframework.stereotype.Service

interface OidcProviderIdFindUseCase {
    fun find(oidcProviderIdFindCommand: OidcProviderIdFindCommand): String
}

@Service
class FindOidcProviderService(
    private val oidcProviders: OidcProviders
): OidcProviderIdFindUseCase {

    override fun find(command: OidcProviderIdFindCommand): String {
        return oidcProviders.findByIdToken(command.idToken, command.provider)
    }
}
