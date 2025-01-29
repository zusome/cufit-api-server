package com.official.cufitapi.domain.notification.appliaction


import com.official.cufitapi.domain.notification.domain.Notification
import com.official.cufitapi.domain.notification.infrastructure.persistence.NotificationJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NotificationQueryService(
    private val notificationJpaRepository: NotificationJpaRepository
) : NotificationFindUseCase {
    override fun findAll(memberId : Long) : List<Notification> {
        // 알람 최신순으로 정렬
        return notificationJpaRepository.findAllByMemberIdOrderByCreatedDateDesc(memberId)
            .map {
                Notification(
                    id = it.id!!,
                    title = it.title,
                    content = it.content,
                    notificationType = it.notificationType,
                    createdDate = it.createdDate!!
                )
            }.toList()
    }


}