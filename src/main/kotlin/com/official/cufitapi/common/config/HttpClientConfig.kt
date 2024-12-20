package com.official.cufitapi.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class HttpClientConfig {

    fun restClientBuilder(): RestClient.Builder =
        RestClient.builder()
}