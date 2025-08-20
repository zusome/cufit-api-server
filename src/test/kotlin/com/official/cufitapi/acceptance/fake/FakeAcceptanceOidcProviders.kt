package com.official.cufitapi.acceptance.fake

import com.official.cufitapi.domain.auth.domain.oidc.OidcProviders
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.atomic.AtomicInteger

@Primary
@Component
@ActiveProfiles("acceptance")
class FakeAcceptanceOidcProviders : OidcProviders {

    private val atomicInteger: AtomicInteger = AtomicInteger(1)

    override fun providerId(idToken: String, provider: String): String {
        return atomicInteger.getAndIncrement().toString()
    }
}
