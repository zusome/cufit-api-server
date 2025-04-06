package com.official.cufitapi.domain.recommendation.infrastructure

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.official.cufitapi.domain.recommendation.domain.AiUserInfo
import com.official.cufitapi.domain.recommendation.domain.UserInfoAiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest


@Component
class UserInfoAiClientAdapter(
    @Value("\${aws.sqs.url}")
    private val sqsUrl: String,
    private val sqsClient: SqsClient
) : UserInfoAiClient {

    private val objectMapper = jacksonObjectMapper()

    override fun register(aiUserInfo: AiUserInfo) {
        val messageBody = mapOf(
            "type" to SqsEventType.REGISTER_PERSON.type,
            "data" to aiUserInfo
        )
        val messageBodyJson = objectMapper.writeValueAsString(messageBody)
        val sendMessageRequest = SendMessageRequest.builder()
            .queueUrl(sqsUrl)
            .messageBody(messageBodyJson)
            .build()
        sqsClient.sendMessage(sendMessageRequest)
    }

    override fun recommend(): AiUserInfo {
        TODO("Not yet implemented")
    }

    enum class SqsEventType(
        val type: String
    ) {
        REGISTER_PERSON("registerPerson"),
        RECOMMEND("recommend")
    }

}