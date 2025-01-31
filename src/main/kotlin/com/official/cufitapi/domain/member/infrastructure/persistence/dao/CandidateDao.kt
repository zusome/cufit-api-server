package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.domain.vo.Gender
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateProfileInfo
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import javax.sql.DataSource

@Component
class CandidateDao(
    dataSource: DataSource,
) {
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    fun profile(candidateId: Long): CandidateProfileInfo {
        val queryParameters = mapOf("candidateId" to candidateId)
        val sql = """
            SELECT
                ideal_mbti,
                ideal_age_range,
                ideal_height_range,
                mbti,
                height,
                station,
                job,
                year_of_birth,
                gender,
                phone_number
            FROM
                match_candidate
            WHERE
                member_id = :candidateId
        """
        sql.trimIndent()
        return namedParameterJdbcTemplate.queryForObject(sql, queryParameters, CandidateProfileInfoMapper())
            ?: throw RuntimeException("Candidate not found")
    }
}

class CandidateProfileInfoMapper : RowMapper<CandidateProfileInfo> {
    override fun mapRow(rs: ResultSet, rowNum: Int): CandidateProfileInfo {
        return CandidateProfileInfo(
            idealMbti = rs.getString("ideal_mbti"),
            idealAgeRange = rs.getString("ideal_age_range"),
            idealHeightRange = rs.getString("ideal_height_range"),
            mbti = rs.getString("mbti"),
            height = rs.getInt("height"),
            station = rs.getString("station"),
            job = rs.getString("job"),
            yearOfBirth = rs.getInt("year_of_birth"),
            gender = Gender.of(rs.getString("gender")),
            phoneNumber = rs.getString("phone_number")
        )
    }
}
