package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class MatchMakerDao(
    dataSource: DataSource,
) {
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    fun findAllOtherCandidates(mathMakerId: Long) {
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
        val bornIn: Int, // 뒤에 2자리
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
