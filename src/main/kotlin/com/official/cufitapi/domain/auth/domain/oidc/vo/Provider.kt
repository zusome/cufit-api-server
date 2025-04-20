package com.official.cufitapi.domain.auth.domain.oidc.vo

enum class Provider(
    val provider: String,
) {
    ADMIN("admin"),
    APPLE("apple"),
    ;

    companion object {
        fun of(provider: String): Provider =
            entries.firstOrNull { it.provider == provider }
                ?: throw IllegalArgumentException("Unknown provider: $provider")
    }
}
