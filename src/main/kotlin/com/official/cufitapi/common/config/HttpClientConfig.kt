package com.official.cufitapi.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class HttpClientConfig {

    @Bean
    fun restClientBuilder(): RestClient.Builder =
        RestClient.builder()

}
