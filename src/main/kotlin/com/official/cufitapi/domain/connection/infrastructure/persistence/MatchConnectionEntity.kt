package com.official.cufitapi.domain.connection.infrastructure.persistence

import com.official.cufitapi.domain.connection.domain.vo.MatchStatus
import com.official.cufitapi.common.jpa.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "match_connection")
class MatchConnectionEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("매칭 신청자")
    val matchMakerId: Long,

    @Comment("보내는 사람")
    val senderId: Long,

    @Comment("받는 사람")
    val receiverId: Long,

    @Comment("매칭 상태")
    var status: MatchStatus = MatchStatus.PROGRESSING
) : BaseTimeEntity() {

    fun updateStatus(status: MatchStatus) {
        this.status = status
    }
}