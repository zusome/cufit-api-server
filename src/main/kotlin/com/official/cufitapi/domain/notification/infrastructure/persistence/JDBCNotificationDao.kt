package com.official.cufitapi.domain.notification.infrastructure.persistence

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.LocalDateTime

@Component
class JDBCNotificationDao(
    private val jdbcClient: JdbcClient,
) : NotificationDao {

    override fun findAll(memberId: Long): List<FindAllNotifications> {
        return jdbcClient.sql(
            """
            SELECT n.*
            FROM notifications n
            WHERE n.receiver_id = :memberId
            ORDER BY n.id DESC
            """
        ).param("memberId", memberId)
            .query(JdbcFindAllNotificationDtoMapper())
            .list()
    }

    private class JdbcFindAllNotificationDtoMapper : RowMapper<FindAllNotifications> {
        override fun mapRow(rs: ResultSet, rowNum: Int): FindAllNotifications? {
            return FindAllNotifications(
                id = rs.getLong("id"),
                receiverId = rs.getLong("receiver_id"),
                title = rs.getString("title"),
                content = rs.getString("content"),
                notificationType = rs.getString("notification_type"),
                payload = rs.getString("payload"),
                createdDate = rs.getTimestamp("created_date").toLocalDateTime(),
                modifiedDate = rs.getTimestamp("modified_date").toLocalDateTime(),
            )
        }
    }
    

    data class FindAllNotifications(
        val id: Long,
        val receiverId: Long,
        val title: String,
        val content: String,
        val notificationType: String,
        val payload: String,
        val createdDate: LocalDateTime,
        val modifiedDate: LocalDateTime
    )
}
