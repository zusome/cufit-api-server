package com.official.cufitapi.auth.domain.vo

enum class Provider(
    val provider: String,
) {
    APPLE("apple"), ;

    companion object {
        fun of(provider: String): Provider =
            entries.firstOrNull { it.provider == provider }
                ?: throw IllegalArgumentException("Unknown provider: $provider")
    }
}
