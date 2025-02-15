package com.official.cufitapi.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.sqs.SqsClient

@Configuration
class SqsClientConfig {

    @Bean
    fun sqsClient(): SqsClient {
        return SqsClient.builder().build()
    }
}