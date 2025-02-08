package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.api.dto.candidate.CandidateImage
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class MatchMakerDao(
    dataSource: DataSource,
) {
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)


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
        val profileImage: String,
        val dateName: String,
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
