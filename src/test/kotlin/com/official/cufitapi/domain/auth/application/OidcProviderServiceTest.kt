package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.FindOidcProviderIdCommand
import com.official.cufitapi.domain.auth.domain.oidc.OidcProviders
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OidcProviderServiceTest {

    @Test
    fun `provider 가 일치하고 idToken 이 정상적이면 providerID를 반환한다`() {
        val fakeOidcProviders =
            FakeOidcProviders(hashMapOf("apple" to hashMapOf("valid-id-token" to "test-provider-id")))
        val oidcProviderService = OidcProviderService(fakeOidcProviders)
        val command = FindOidcProviderIdCommand(idToken = "valid-id-token", provider = "apple")

        val providerId = oidcProviderService.providerId(command)

        assertThat(providerId).isEqualTo("test-provider-id")
    }

    @Test
    fun `idToken 이 정상적이지 않는다면 에러를 발생한다`() {
        val fakeOidcProviders =
            FakeOidcProviders(hashMapOf("apple" to hashMapOf("valid-id-token" to "test-provider-id")))
        val oidcProviderService = OidcProviderService(fakeOidcProviders)
        val command = FindOidcProviderIdCommand(idToken = "apple-id-token", provider = "apple")

        assertThrows<RuntimeException> {
            oidcProviderService.providerId(command)
        }
    }

    @Test
    fun `provider 가 일치하지 않는다면 에러를 발생한다`() {
        val fakeOidcProviders =
            FakeOidcProviders(hashMapOf("google" to hashMapOf("valid-id-token" to "test-provider-id")))
        val oidcProviderService = OidcProviderService(fakeOidcProviders)
        val command = FindOidcProviderIdCommand(idToken = "invalid-token", provider = "apple")

        assertThrows<RuntimeException> {
            oidcProviderService.providerId(command)
        }
    }

    class FakeOidcProviders(
        private val providers: HashMap<String, HashMap<String, String>> = hashMapOf(),
    ) : OidcProviders {

        override fun providerId(idToken: String, provider: String): String {
            return providers.get(provider)?.get(idToken) ?: throw RuntimeException()
        }
    }
}
