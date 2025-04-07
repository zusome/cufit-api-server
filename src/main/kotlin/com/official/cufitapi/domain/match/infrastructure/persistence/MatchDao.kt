package com.official.cufitapi.domain.match.infrastructure.persistence

import com.official.cufitapi.common.DateTimeUtils
import com.official.cufitapi.common.tomorrow
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import javax.sql.DataSource

@Component
class MatchDao(
    dataSource: DataSource,
) {
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    data class MatchCandidates(
        val candidates: List<MatchCandidate>,
    )

    data class MatchCandidate(
        val image: String,
        val candidateName: String,
        val relationType: String,
        val age: Int,
        val remainingMatchCount: Int,
    )

    /**
     * 타켓을 제외한 내 후보자들 중에 주선 가능한 후보자 조회한다.
     * 후보자 이름 + 관계 + 나이 + 주선자가 보낼 수 있는 연결 요청 남은 횟수(금일)
     * 요청을 다 보낸 사용자의 경우 disabled 되어야 함
     * 이미 주선을 보냈었다면 보여지지도 않아야 함
     */
    fun findAllByMatchId(makerId: Long, targetId: Long): MatchCandidates {
        // 내 후보자 조회
        return MatchCandidates(
            listOf(
                MatchCandidate(
                    "https://cataas.com/cat",
                    "내후보자",
                    "COMPANION",
                    1996,
                    2
                ),
                MatchCandidate(
                    "https://cataas.com/cat",
                    "내후보자2",
                    "FRIEND",
                    2000,
                    3
                ),
                MatchCandidate(
                    "https://cataas.com/cat",
                    "내후보자3",
                    "ACQUAINTANCE",
                    1998,
                    1
                )
            )
        )
    }

    private fun makerCandidateRelations(
        makerId: Long,
        targetId: Long,
    ): Map<Long, MakerCandidateRelation> {
        val firstQueryParameters = MapSqlParameterSource()
            .addValue("makerId", makerId)
            .addValue("targetId", targetId)
        val firstSql = """
                SELECT 
                    mr.invitee_id,
                    mr.relation_type
                FROM
                    member_relations mr
                WHERE 
                    mr.inviter_id = :makerId
                AND
                    mr.invitee_id != :targetId 
            """.trimIndent()
        return namedParameterJdbcTemplate.query(
            firstSql, firstQueryParameters, MakerCandidateRelationMapper()
        ).associateBy(MakerCandidateRelation::inviteeId)
    }

    private fun availableCandidateIds(
        makerId: Long,
        candidateIds: Set<Long>,
    ): MutableMap<Long, Int> {
        val existLeftCandidateIds = existsLeftCandidateIds(makerId, candidateIds)
        val existRightCandidateIds = existsRightCandidateIds(makerId, candidateIds)
        val resultMap = mutableMapOf<Long, Int>()
        for ((key, value) in existLeftCandidateIds) {
            resultMap[key] = value
        }
        for ((key, value) in existRightCandidateIds) {
            resultMap.merge(key, value) { oldValue, newValue -> oldValue + newValue }
        }
        candidateIds.forEach { resultMap.putIfAbsent(it, 0) }
        return resultMap
    }

    private fun candidates(candidateIds: Set<Long>): Map<Long, Candidate> {
        val secondQueryParameters = MapSqlParameterSource()
            .addValue("candidateIds", candidateIds)
        val secondSql = """
                SELECT 
                    mc.member_id,
                    m.name,
                    mc.year_of_birth
                FROM
                    candidate mc
                JOIN 
                    member m ON mc.member_id = m.id
                AND
                    mc.is_match_agreed = true
                AND
                    mc.year_of_birth IS NOT NULL
                AND
                    mc.member_id IN (:candidateIds)
            """.trimIndent()
        return namedParameterJdbcTemplate.query(secondSql, secondQueryParameters, CandidateMapper())
            .associateBy(Candidate::memberId)
    }

    private fun existedMatchCandidates(
        makerId: Long,
        targetId: Long,
        inviteeIds: Set<Long>,
    ): MutableList<Long> {
        if(inviteeIds.isEmpty()){
            return mutableListOf()
        }
        val thirdQueryParameters = MapSqlParameterSource()
            .addValue("makerId", makerId)
            .addValue("leftTargetId", targetId)
            .addValue("rightTargetId", targetId)
            .addValue("inviteeIds", inviteeIds)

        val thirdSql = """
                SELECT
                    left_candidate_id,
                    right_candidate_id
                FROM 
                    matches a
                WHERE
                    a.maker_id = :makerId
                AND
                (
                    (a.left_candidate_id = :leftTargetId AND a.right_candidate_id in (:inviteeIds))
                    OR
                    (a.right_candidate_id = :rightTargetId AND a.left_candidate_id in (:inviteeIds))
                )
            """.trimIndent()
        val matches = namedParameterJdbcTemplate.query(thirdSql, thirdQueryParameters, MatchMapper())

        val leftExistCandidate = matches
            .filter { it.leftCandidateId == targetId }
            .map { it.leftCandidateId }
            .toMutableList()
        val rightExistCandidate = matches
            .filter { it.rightCandidateId == targetId }
            .map { it.rightCandidateId }
            .toMutableList()
        leftExistCandidate.addAll(rightExistCandidate)
        return leftExistCandidate
    }

    private fun existsLeftCandidateIds(
        makerId: Long,
        inviteeIds: Set<Long>,
    ): MutableMap<Long, Int> {
        val fourthQueryParameters = MapSqlParameterSource()
            .addValue("makerId", makerId)
            .addValue("inviteeIds", inviteeIds)
            .addValue("today", DateTimeUtils.beginToday())
            .addValue("tomorrow", DateTimeUtils.beginToday().tomorrow())

        val fourthQuery = """
                SELECT 
                    a.left_candidate_id
                FROM
                    matches a
                WHERE
                    a.maker_id = :makerId
                AND
                    a.left_candidate_id in (:inviteeIds)
                AND
                    a.created_date BETWEEN :today AND :tomorrow
            """.trimIndent()

        // group by id count
        val candidateIds = namedParameterJdbcTemplate.queryForList(fourthQuery, fourthQueryParameters, Long::class.java)
        return candidateIds.groupBy { it }
            .mapValues { it.value.size }
            .toMutableMap()
    }

    private fun existsRightCandidateIds(
        makerId: Long,
        inviteeIds: Set<Long>,
    ): MutableMap<Long, Int> {
        val fourthQueryParameters = MapSqlParameterSource()
            .addValue("makerId", makerId)
            .addValue("inviteeIds", inviteeIds)
            .addValue("today", DateTimeUtils.beginToday())
            .addValue("tomorrow", DateTimeUtils.beginToday().tomorrow())

        val fourthQuery = """
                SELECT 
                    a.right_candidate_id
                FROM
                    matches a
                WHERE
                    a.maker_id = :makerId
                AND
                    a.right_candidate_id in (:inviteeIds)
                AND
                    a.created_date BETWEEN :today AND :tomorrow
            """.trimIndent()

        // group by id count
        return namedParameterJdbcTemplate.queryForList(fourthQuery, fourthQueryParameters, Long::class.java)
            .groupBy { it }
            .mapValues { it.value.size }
            .toMutableMap()
    }
}

class MakerCandidateRelationMapper : RowMapper<MakerCandidateRelation> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MakerCandidateRelation {
        return MakerCandidateRelation(
            rs.getLong("invitee_id"),
            rs.getString("relation_type"),
        )
    }
}

data class MakerCandidateRelation(
    val inviteeId: Long,
    val relationType: String,
)

class CandidateMapper : RowMapper<Candidate> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Candidate {
        return Candidate(
            rs.getLong("member_id"),
            rs.getString("name"),
            rs.getInt("year_of_birth"),
        )
    }
}

data class Candidate(
    val memberId: Long,
    val name: String,
    val yearOfBirth: Int,
)

class MatchMapper : RowMapper<Match> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Match {
        return Match(
            rs.getLong("left_candidate_id"),
            rs.getLong("right_candidate_id"),
        )
    }
}

data class Match(
    val leftCandidateId: Long,
    val rightCandidateId: Long,
)
