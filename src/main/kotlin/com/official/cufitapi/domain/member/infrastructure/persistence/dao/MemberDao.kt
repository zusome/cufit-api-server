package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.api.dto.MemberTypeInfo
import com.official.cufitapi.domain.member.domain.vo.Gender
import com.official.cufitapi.domain.member.domain.vo.MakerCandidateRelationType
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateProfileInfo
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
                members
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
                inviter.name AS inviterName,
                mr.relation_type AS relationWithInvitee
            FROM
                members m
            LEFT JOIN
                member_relations mr ON m.id = mr.invitee_id
            LEFT JOIN 
                members inviter ON mr.inviter_id = inviter.id
            WHERE
                m.id = :memberId
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
        MakerCandidateRelationType.of(rs.getString("relationWithInvitee"))
    )
}

class CandidateProfileInfoMapper : RowMapper<CandidateProfileInfo> {
    override fun mapRow(rs: ResultSet, rowNum: Int): CandidateProfileInfo {
        return CandidateProfileInfo(
            idealMbti = rs.getString("ideal_mbti"),
            idealAgeRange = rs.getString("ideal_age_range"),
            idealHeightRange = rs.getString("ideal_height_range"),
            mbti = rs.getString("mbti"),
            height = rs.getInt("height"),
            city = rs.getString("city"),
            district = rs.getString("district"),
            job = rs.getString("job"),
            yearOfBirth = rs.getInt("year_of_birth"),
            gender = Gender.of(rs.getString("gender")),
            phoneNumber = rs.getString("phone_number"),
            hobbies = rs.getString("hobbies"),
            smoke = rs.getInt("smoke"),
            drink = rs.getInt("drink"),
        )
    }
}
