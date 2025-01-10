package com.official.cufitapi.notification.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.HttpResponse
import com.official.cufitapi.domain.api.ApiV1Controller
import com.official.cufitapi.domain.api.docs.NotificationApiDocs
import com.official.cufitapi.notification.api.dto.NotificationResponse
import com.official.cufitapi.notification.application.NotificationGetUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class NotificationApi(
    private val notificationGetUseCase: NotificationGetUseCase
) : NotificationApiDocs {

    @GetMapping("/notifications")
    fun getNotificationList(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser
    ) : HttpResponse<List<NotificationResponse>> {
        val notifications = notificationGetUseCase.findAll(authorizationUser.userId)
        val responseBody = notifications.map {
            NotificationResponse.of(it)
        }
        return HttpResponse.Companion.of(HttpStatus.OK, responseBody)
    }
}