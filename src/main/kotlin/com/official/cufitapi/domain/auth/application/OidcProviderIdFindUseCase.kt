package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.OidcProviderIdFindCommand
import com.official.cufitapi.domain.auth.domain.repository.OidcProviderIdClient
import org.springframework.stereotype.Service

interface OidcProviderIdFindUseCase {
    fun find(oidcProviderIdFindCommand: OidcProviderIdFindCommand): String
}

@Service
class OidcProviderIdFindService(
    private val oidcProviderIdClient: OidcProviderIdClient
): OidcProviderIdFindUseCase {

    override fun find(command: OidcProviderIdFindCommand): String {
        return oidcProviderIdClient.findByIdToken(command.idToken, command.provider)
    }
}
