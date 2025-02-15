package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.CANDIDATE_COUNT_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.OTHER_CANDIDATE_COUNT_SQL
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Component

@Component
class MatchMakerJdbcClientDao(
    private val jdbcClient: JdbcClient,
) : MatchMaker {

    override fun candidateCount(memberId: Long): Long {
        return jdbcClient.sql(CANDIDATE_COUNT_SQL)
            .param("memberId", memberId)
            .query { rs, _ -> rs.getLong(1) }
            .single()
    }

    override fun otherCandidateCount(memberId: Long): Long {
        return jdbcClient.sql(OTHER_CANDIDATE_COUNT_SQL)
            .param("memberId", memberId)
            .query { rs, _ -> rs.getLong(1) }
            .single()
    }
}
