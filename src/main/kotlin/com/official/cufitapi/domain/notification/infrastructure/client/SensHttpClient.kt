package com.official.cufitapi.domain.notification.infrastructure.client

import com.official.cufitapi.common.config.property.SensProperties
import com.official.cufitapi.domain.notification.infrastructure.client.cipher.SensCipher
import com.official.cufitapi.domain.notification.infrastructure.client.dto.SensMessageRequest
import com.official.cufitapi.domain.notification.infrastructure.client.dto.SensMessageResponse
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.net.URI

// @Component
class SensHttpClient(
    private val sensCipher: SensCipher,
    private val sensProperties: SensProperties,
    restClientBuilder: RestClient.Builder,
) {
    private val restClient = restClientBuilder
        .baseUrl(sensProperties.hostUrl)
        .build()

    fun sendMessage(messageRequest: SensMessageRequest): Result<SensMessageResponse> {
        val hostUrl = "/sms/v2/services/${sensProperties.serviceId}/messages"
        val timestamp = System.currentTimeMillis().toString()
        val accessKey = sensProperties.accessKey
        val signature = apiGatewaySignature(hostUrl, timestamp, POST.name(), accessKey)
        return runCatching {
            restClient.post()
                .uri(URI.create(hostUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .body(messageRequest)
                .headers { headers ->
                    headers["x-ncp-apigw-timestamp"] = timestamp
                    headers["x-ncp-iam-access-key"] = accessKey
                    headers["x-ncp-apigw-signature-v2"] = signature
                }
                .retrieve()
                .body(SensMessageResponse::class.java)!!
        }
    }

    /**
     * API Gateway 서명을 생성합니다.
     * @param url 요청 URL
     * @param timestamp 타임스탬프
     * @param method HTTP 메서드
     * @param accessKey 액세스 키
     * @return 생성된 API Gateway 서명
     */
    private fun apiGatewaySignature(
        url: String,
        timestamp: String,
        method: String,
        accessKey: String,
    ): String {
        val message = StringBuilder()
            .apply { append(method) }
            .apply { append(SPACE) }
            .apply { append(url) }
            .apply { append(NEW_LINE) }
            .apply { append(timestamp) }
            .apply { append(NEW_LINE) }
            .apply { append(accessKey) }
            .let { toString() }
        return sensCipher.encode(message)
    }

    companion object {
        private const val SPACE = " "
        private const val NEW_LINE = "\n"
    }
}
