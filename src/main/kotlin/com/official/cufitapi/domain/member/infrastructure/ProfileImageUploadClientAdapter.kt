package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.CufitException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.net.URI
import java.util.*
import kotlin.math.log

@Component
class ProfileImageUploadClientAdapter(
    @Value("\${aws.s3.presigned-url}")
    private val PROFILE_IMAGE_UPLOAD_URL: String
) {
    private val restClient: RestClient = RestClient.create()

    private val logger = LoggerFactory.getLogger(ProfileImageUploadClientAdapter::class.java)

    companion object {
        private const val OBJECT_KEY = "objectKey"
        private const val METHOD = "method"
        private const val METHOD_PUT_OBJECT = "put_object"
    }

    fun getImageUploadPresignedUrl(): String? {
        val fileKey = UUID.randomUUID().toString() + ".jpg"
        val responseBody = restClient.get()
            .uri(URI.create("$PROFILE_IMAGE_UPLOAD_URL?$OBJECT_KEY=$fileKey&$METHOD=$METHOD_PUT_OBJECT"))
            .retrieve()
            .onStatus(
                { it.is4xxClientError },
                { _, _ ->
                    logger.error("Failed to get presigned url")
                    throw CufitException(ErrorCode.INTERNAL_SERVER_ERROR) }
            )
            .onStatus(
                { it.is5xxServerError },
                { _, _ ->
                    logger.error("Failed to get presigned url")
                    throw CufitException(ErrorCode.INTERNAL_SERVER_ERROR)
                }
            )
            .body(ProfileImageUploadResponse::class.java)
        if (responseBody == null) {
            throw CufitException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
        return responseBody.presignedUrl
    }
}

data class ProfileImageUploadResponse(
    val presignedUrl: String
)