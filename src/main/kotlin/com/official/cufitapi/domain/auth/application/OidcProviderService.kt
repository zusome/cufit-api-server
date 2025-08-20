package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.FindOidcProviderIdCommand
import com.official.cufitapi.domain.auth.domain.oidc.OidcProviders
import org.springframework.stereotype.Service

@FunctionalInterface
interface OidcProviderIdFindUseCase {
    fun providerId(findOidcProviderIdCommand: FindOidcProviderIdCommand): String
}

@Service
class OidcProviderService(
    private val oidcProviders: OidcProviders
): OidcProviderIdFindUseCase {

    override fun providerId(command: FindOidcProviderIdCommand): String {
        return oidcProviders.providerId(command.idToken, command.provider)
    }
}
