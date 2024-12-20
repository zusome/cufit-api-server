package com.official.cufitapi.auth.application

import com.official.cufitapi.auth.application.command.OidcProviderIdFindCommand
import com.official.cufitapi.auth.domain.repository.OidcProviderIdClient
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
