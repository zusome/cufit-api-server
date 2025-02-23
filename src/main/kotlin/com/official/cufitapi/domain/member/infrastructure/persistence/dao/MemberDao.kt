package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.api.dto.MemberTypeInfo
import com.official.cufitapi.domain.member.domain.vo.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberInfoResponse
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import javax.sql.DataSource

@Component
class MemberDao(
    dataSource: DataSource,
) {
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    fun memberType(memberId: Long): MemberTypeInfo {
        val queryParameters = MapSqlParameterSource().addValue("memberId", memberId)
        val sql = """
            SELECT
                authority AS member_type
            FROM
                member
            WHERE
                id = :memberId
        """.trimIndent()
        return namedParameterJdbcTemplate.queryForObject(sql, queryParameters, MemberTypeInfoMapper())
            ?: throw RuntimeException("Member not found")
    }

    fun relationInfo(memberId: Long): MemberInfoResponse? {
        val queryParameters = MapSqlParameterSource()
            .addValue("memberId", memberId)
        val sql = """
            SELECT
                m.name,
                m.email,
                inviter.email AS inviterName,
                mr.relation_type AS relationWithInvitee
            FROM
                member m
            LEFT JOIN
                member_relations mr ON m.id = mr.invitee_id
            LEFT JOIN 
                member inviter ON mr.inviter_id = inviter.id
            WHERE
                id = :memberId
        """.trimIndent()
        return namedParameterJdbcTemplate.queryForObject(sql, queryParameters, MemberInfoResponseMapper())
    }
}

class MemberTypeInfoMapper : RowMapper<MemberTypeInfo> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MemberTypeInfo = MemberTypeInfo(
        rs.getString("member_type")
    )
}

class MemberInfoResponseMapper : RowMapper<MemberInfoResponse> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MemberInfoResponse = MemberInfoResponse(
        rs.getString("name"),
        rs.getString("email"),
        rs.getString("inviterName"),
        MatchMakerCandidateRelationType.of(rs.getString("relationWithInvitee"))
    )
}
