package com.official.cufitapi.domain.notification.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.notification.api.docs.NotificationApiDocs
import com.official.cufitapi.domain.notification.api.dto.NotificationResponse
import com.official.cufitapi.domain.notification.appliaction.NotificationQueryService
import com.official.cufitapi.domain.notification.appliaction.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class NotificationApi(
    private val notificationQueryService: NotificationQueryService,
    private val notificationService: NotificationService
) : NotificationApiDocs {

    @GetMapping("/notifications")
    fun getNotificationList(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser
    ): HttpResponse<List<NotificationResponse>> {
        return HttpResponse.of(HttpStatus.OK, notificationQueryService.findAll(authorizationUser.userId))
    }
}