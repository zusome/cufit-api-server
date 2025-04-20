package com.official.cufitapi.domain.notification.api.http

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.notification.api.http.docs.NotificationApiDocs
import com.official.cufitapi.domain.notification.api.http.dto.NotificationResponse
import com.official.cufitapi.domain.notification.application.temp.NotificationFindUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping

// @ApiV1Controller
// class NotificationApi(
//     private val notificationFindUseCase: NotificationFindUseCase,
// ) : NotificationApiDocs {
//
//     @GetMapping("/notifications")
//     override fun getNotificationList(
//         @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser
//     ): HttpResponse<List<NotificationResponse>> {
//         val notifications = notificationFindUseCase.findAll(authorizationUser.userId)
//         return HttpResponse.of(
//             HttpStatus.OK,
//             notifications.map {
//                 NotificationResponse(
//                     id = it.id,
//                     title = it.title,
//                     content = it.content,
//                     notificationType = it.notificationType,
//                     createdDate = it.createdDate
//                 )
//             }
//         )
//     }
// }
