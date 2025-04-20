package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.FindOidcProviderIdCommand
import com.official.cufitapi.domain.auth.domain.repository.OidcProviders
import org.springframework.stereotype.Service

interface OidcProviderIdFindUseCase {
    fun find(findOidcProviderIdCommand: FindOidcProviderIdCommand): String
}

@Service
class OidcProviderService(
    private val oidcProviders: OidcProviders
): OidcProviderIdFindUseCase {

    override fun find(command: FindOidcProviderIdCommand): String {
        return oidcProviders.findByIdToken(command.idToken, command.provider)
    }
}
