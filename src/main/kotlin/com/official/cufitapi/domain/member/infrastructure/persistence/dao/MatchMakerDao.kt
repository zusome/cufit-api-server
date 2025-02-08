package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class MatchMakerDao(
    dataSource: DataSource,
) {

    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    fun otherCandidateCount(memberId: Long): Long {
        val query = """
            SELECT
                COUNT(*)
            FROM
                match_candidate
            JOIN 
                member_relations ON match_candidate.member_id = member_relations.invitee_id
            WHERE
                member_relations.inviter_id != :memberId
        """.trimIndent()
        return namedParameterJdbcTemplate.queryForObject(
            query,
            mapOf("memberId" to memberId),
            Long::class.java
        ) ?: throw RuntimeException("Candidate not found")
    }

    fun candidateCount(memberId: Long): Long {
        val query = """
            SELECT
                COUNT(*)
            FROM
                match_candidate
            JOIN 
                member_relations ON match_candidate.member_id = member_relations.invitee_id
            WHERE
                member_relations.inviter_id = :memberId
        """.trimIndent()
        return namedParameterJdbcTemplate.queryForObject(
            query,
            mapOf("memberId" to memberId),
            Long::class.java
        ) ?: throw RuntimeException("Candidate not found")
    }

    data class MatchCandidates(
        val candidates: List<MatchCandidate>,
    )

    data class MatchCandidate(
        val image: String,
        val name: String,
        val relation: String,
        val arrangements: List<ArrangementInfo>,
        val hasProfile: Boolean,
        val isMatchingPaused: Boolean
    )

    data class ArrangementInfo(
        val image: String,
        val name: String,
        val arrangementStatus: String,
    )

    data class OtherMatchCandidates(
        val candidates: List<OtherMatchCandidate>,
    )

    data class OtherMatchCandidate(
        val id: Long,
        val images: List<CandidateImage>,
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
