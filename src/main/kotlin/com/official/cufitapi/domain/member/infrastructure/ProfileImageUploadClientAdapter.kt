package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.common.exception.CufitException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.net.URI
import java.util.*

@Component
class ProfileImageUploadClientAdapter(
    @Value("\${aws.s3.presigned-url}")
    private val PROFILE_IMAGE_UPLOAD_URL: String
) {
    private val restClient: RestClient = RestClient.create()

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
                { _, _ -> throw CufitException("Failed to get presigned url") }
            )
            .onStatus(
                { it.is5xxServerError },
                { _, _ -> throw CufitException("Failed to get presigned url") }
            )
            .body(ProfileImageUploadResponse::class.java)
        if (responseBody == null) {
            throw CufitException("Failed to get presigned url")
        }
        return responseBody.presignedUrl
    }
}

data class ProfileImageUploadResponse(
    val presignedUrl: String
)