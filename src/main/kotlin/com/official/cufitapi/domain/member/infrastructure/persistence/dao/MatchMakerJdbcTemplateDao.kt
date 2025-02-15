package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.CANDIDATE_COUNT_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.OTHER_CANDIDATE_COUNT_SQL
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class MatchMakerJdbcTemplateDao(
    private val dataSource: DataSource,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource),
): MatchMaker {

    override fun otherCandidateCount(memberId: Long): Long {
        return namedParameterJdbcTemplate.queryForObject(
            OTHER_CANDIDATE_COUNT_SQL,
            mapOf("memberId" to memberId),
            Long::class.java
        ) ?: throw RuntimeException("Candidate not found")
    }

    override fun candidateCount(memberId: Long): Long {
        return namedParameterJdbcTemplate.queryForObject(
            CANDIDATE_COUNT_SQL,
            mapOf("memberId" to memberId),
            Long::class.java
        ) ?: throw RuntimeException("Candidate not found")
    }
}
