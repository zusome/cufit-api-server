package com.official.cufitapi.domain.arrangement.infrastructure.persistence

import com.official.cufitapi.common.DateTimeUtils
import com.official.cufitapi.common.tomorrow
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementDao.ArrangementCandidates
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.LocalDate
import javax.sql.DataSource

@Component
class ArrangementDao(
    dataSource: DataSource,
) {
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    data class ArrangementCandidates(
        val candidates: List<ArrangementCandidate>,
    )

    data class ArrangementCandidate(
        val image: String,
        val candidateName: String,
        val relationType: String,
        val age: Int,
        val remainingArrangementCount: Int,
    )

    /**
     * 타켓을 제외한 내 후보자들 중에 주선 가능한 후보자 조회한다.
     * 후보자 이름 + 관계 + 나이 + 주선자가 보낼 수 있는 연결 요청 남은 횟수(금일)
     * 요청을 다 보낸 사용자의 경우 disabled 되어야 함
     * 이미 주선을 보냈었다면 보여지지도 않아야 함
     */
    fun findAllByArrangementId(matchMakerId: Long, targetId: Long): ArrangementCandidates {
        // 내 후보자 조회
        return ArrangementCandidates(
            listOf(
                ArrangementCandidate(
                    "https://cataas.com/cat",
                    "내후보자",
                    "COMPANION",
                    1996,
                    2
                ),
                ArrangementCandidate(
                    "https://cataas.com/cat",
                    "내후보자2",
                    "FRIEND",
                    2000,
                    3
                ),
                ArrangementCandidate(
                    "https://cataas.com/cat",
                    "내후보자3",
                    "ACQUAINTANCE",
                    1998,
                    1
                )
            )
        )
        // val matchMakerCandidateRelations = matchMakerCandidateRelations(matchMakerId, targetId)
        // // 주선 가능한 후보자 조회
        // val availableMatchMakerCandidateRelations = matchMakerCandidateRelations.filter { matchMakerCandidateRelations.containsKey(it.key) }
        // val existedArrangementCandidates = existedArrangementCandidates(matchMakerId, targetId, availableMatchMakerCandidateRelations.keys)
        // val realAvailableMatchMakerCandidateRelations = availableMatchMakerCandidateRelations.filterKeys { !existedArrangementCandidates.contains(it) }
        // val candidateArrangementCountMap = availableCandidateIds(matchMakerId, realAvailableMatchMakerCandidateRelations.keys)
        // val matchCandidates = matchCandidates(realAvailableMatchMakerCandidateRelations.keys)
        // return matchCandidates.map { (memberId, matchCandidate) ->
        //
        //     ArrangementCandidate(
        //         "image",
        //         matchCandidate.name,
        //         realAvailableMatchMakerCandidateRelations[memberId]!!.relationType,
        //         matchCandidate.yearOfBirth,
        //         3 - (candidateArrangementCountMap[memberId] ?: 0)
        //     )
        // }.let { ArrangementCandidates(it) }
    }

    private fun matchMakerCandidateRelations(
        matchMakerId: Long,
        targetId: Long,
    ): Map<Long, MatchMakerCandidateRelation> {
        val firstQueryParameters = MapSqlParameterSource()
            .addValue("matchMakerId", matchMakerId)
            .addValue("targetId", targetId)
        val firstSql = """
                SELECT 
                    mr.invitee_id,
                    mr.relation_type
                FROM
                    member_relations mr
                WHERE 
                    mr.inviter_id = :matchMakerId
                AND
                    mr.invitee_id != :targetId 
            """.trimIndent()
        return namedParameterJdbcTemplate.query(
            firstSql, firstQueryParameters, MatchMakerCandidateRelationMapper()
        ).associateBy(MatchMakerCandidateRelation::inviteeId)
    }

    private fun availableCandidateIds(
        matchMakerId: Long,
        candidateIds: Set<Long>,
    ): MutableMap<Long, Int> {
        val existLeftCandidateIds = existsLeftCandidateIds(matchMakerId, candidateIds)
        val existRightCandidateIds = existsRightCandidateIds(matchMakerId, candidateIds)
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

    private fun matchCandidates(candidateIds: Set<Long>): Map<Long, MatchCandidate> {
        val secondQueryParameters = MapSqlParameterSource()
            .addValue("candidateIds", candidateIds)
        val secondSql = """
                SELECT 
                    mc.member_id,
                    m.name,
                    mc.year_of_birth
                FROM
                    match_candidate mc
                JOIN 
                    member m ON mc.member_id = m.id
                AND
                    mc.is_match_agreed = true
                AND
                    mc.year_of_birth IS NOT NULL
                AND
                    mc.member_id IN (:candidateIds)
            """.trimIndent()
        return namedParameterJdbcTemplate.query(secondSql, secondQueryParameters, MatchCandidateMapper())
            .associateBy(MatchCandidate::memberId)
    }

    private fun existedArrangementCandidates(
        matchMakerId: Long,
        targetId: Long,
        inviteeIds: Set<Long>,
    ): MutableList<Long> {
        if(inviteeIds.isEmpty()){
            return mutableListOf()
        }
        val thirdQueryParameters = MapSqlParameterSource()
            .addValue("matchMakerId", matchMakerId)
            .addValue("leftTargetId", targetId)
            .addValue("rightTargetId", targetId)
            .addValue("inviteeIds", inviteeIds)

        val thirdSql = """
                SELECT
                    left_candidate_id,
                    right_candidate_id
                FROM 
                    arrangements a
                WHERE
                    a.match_maker_id = :matchMakerId
                AND
                (
                    (a.left_candidate_id = :leftTargetId AND a.right_candidate_id in (:inviteeIds))
                    OR
                    (a.right_candidate_id = :rightTargetId AND a.left_candidate_id in (:inviteeIds))
                )
            """.trimIndent()
        val arrangements = namedParameterJdbcTemplate.query(thirdSql, thirdQueryParameters, ArrangementMapper())

        val leftExistCandidate = arrangements
            .filter { it.leftCandidateId == targetId }
            .map { it.leftCandidateId }
            .toMutableList()
        val rightExistCandidate = arrangements
            .filter { it.rightCandidateId == targetId }
            .map { it.rightCandidateId }
            .toMutableList()
        leftExistCandidate.addAll(rightExistCandidate)
        return leftExistCandidate
    }

    private fun existsLeftCandidateIds(
        matchMakerId: Long,
        inviteeIds: Set<Long>,
    ): MutableMap<Long, Int> {
        val fourthQueryParameters = MapSqlParameterSource()
            .addValue("matchMakerId", matchMakerId)
            .addValue("inviteeIds", inviteeIds)
            .addValue("today", DateTimeUtils.beginToday())
            .addValue("tomorrow", DateTimeUtils.beginToday().tomorrow())

        val fourthQuery = """
                SELECT 
                    a.left_candidate_id
                FROM
                    arrangements a
                WHERE
                    a.match_maker_id = :matchMakerId
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
        matchMakerId: Long,
        inviteeIds: Set<Long>,
    ): MutableMap<Long, Int> {
        val fourthQueryParameters = MapSqlParameterSource()
            .addValue("matchMakerId", matchMakerId)
            .addValue("inviteeIds", inviteeIds)
            .addValue("today", DateTimeUtils.beginToday())
            .addValue("tomorrow", DateTimeUtils.beginToday().tomorrow())

        val fourthQuery = """
                SELECT 
                    a.right_candidate_id
                FROM
                    arrangements a
                WHERE
                    a.match_maker_id = :matchMakerId
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

class MatchMakerCandidateRelationMapper : RowMapper<MatchMakerCandidateRelation> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MatchMakerCandidateRelation {
        return MatchMakerCandidateRelation(
            rs.getLong("invitee_id"),
            rs.getString("relation_type"),
        )
    }
}

data class MatchMakerCandidateRelation(
    val inviteeId: Long,
    val relationType: String,
)

class MatchCandidateMapper : RowMapper<MatchCandidate> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MatchCandidate {
        return MatchCandidate(
            rs.getLong("member_id"),
            rs.getString("name"),
            rs.getInt("year_of_birth"),
        )
    }
}

data class MatchCandidate(
    val memberId: Long,
    val name: String,
    val yearOfBirth: Int,
)

class ArrangementMapper : RowMapper<Arrangement> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Arrangement {
        return Arrangement(
            rs.getLong("left_candidate_id"),
            rs.getLong("right_candidate_id"),
        )
    }
}

data class Arrangement(
    val leftCandidateId: Long,
    val rightCandidateId: Long,
)
