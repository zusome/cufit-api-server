package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class MatchMakerDao(
    dataSource: DataSource,
) {
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    fun findAllCandidates(matchMakerId: Long): List<MatchCandidate> {
        val sql = """
        SELECT c.name, r.relation, a.dateName, a.arrangementStatus, c.hasProfile, c.isMatchingPaused
        FROM MatchCandidateEntity c
        LEFT JOIN MatchRelationEntity r ON c.id = r.candidateId
        LEFT JOIN MatchArrangementEntity a ON c.id = a.candidateId
        WHERE r.matchMakerId = :matchMakerId
    """
        val params = mapOf("matchMakerId" to matchMakerId)
        return namedParameterJdbcTemplate.query(sql, params) { rs, _ ->
            MatchCandidate(
                name = rs.getString("name"),
                relation = rs.getString("relation"),
                arrangements = listOf(
                    ArrangementInfo(
                        dateName = rs.getString("dateName"),
                        arrangementStatus = rs.getString("arrangementStatus")
                    )
                ),
                hasProfile = rs.getBoolean("hasProfile"),
                isMatchingPaused = rs.getBoolean("isMatchingPaused")
            )
        }
    }


    fun findAllOtherCandidates(matchMakerId: Long): List<OtherMatchCandidate> {
        val sql = """
        SELECT c.* FROM MatchCandidateEntity c
        LEFT JOIN MatchRelationEntity r ON c.id = r.candidateId
        WHERE r.matchMakerId IS NULL OR r.matchMakerId != :matchMakerId
    """
        val params = mapOf("matchMakerId" to matchMakerId)
        return namedParameterJdbcTemplate.query(sql, params) { rs, _ ->
            OtherMatchCandidate(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                yearOfBirth = rs.getInt("yearOfBirth"),
                mbti = rs.getString("mbti"),
                height = rs.getInt("height"),
                station = rs.getString("station"),
                job = rs.getString("job"),
                degrees = rs.getInt("degrees"),
                matchMakerRelation = rs.getString("matchMakerRelation"),
                matchMakerName = rs.getString("matchMakerName"),
                idealHeightRange = rs.getString("idealHeightRange").split(",").map { it.toInt() },
                idealAgeRange = rs.getString("idealAgeRange").split(","),
                idealMbti = rs.getString("idealMbti").split(",")
            )
        }
    }

    data class MatchCandidates(
        val candidates: List<MatchCandidate>,
    )

    data class MatchCandidate(
        val name: String,
        val relation: String,
        val arrangements: List<ArrangementInfo>,
        val hasProfile: Boolean,
        val isMatchingPaused: Boolean
    )

    data class ArrangementInfo(
        val dateName: String,
        val arrangementStatus: String,
    )

    data class OtherMatchCandidates(
        val candidates: List<OtherMatchCandidate>,
    )

    data class OtherMatchCandidate(
        val id: Long,
        val name: String,
        val yearOfBirth: Int,
        val mbti: String,
        val height: Int,
        val station: String,
        val job: String,
        val degrees: Int,
        val matchMakerRelation: String,
        val matchMakerName: String,
        val idealHeightRange: List<Int>,
        val idealAgeRange: List<String>,
        val idealMbti: List<String>
    )
}
