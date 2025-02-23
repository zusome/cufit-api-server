package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.infrastructure.persistence.dto.ArrangementDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.ArrangementInfo
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidateDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidateImageDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberRelationDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherMatchCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.ArrangementDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.MatchCandidateDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.MatchCandidateImageDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.MemberDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.MemberRelationDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.CANDIDATE_COUNT_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.OTHER_CANDIDATE_COUNT_SQL
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Component

@Component
class MatchMakerDaoJdbcClientDao(
    private val jdbcClient: JdbcClient,
) : MatchMakerDao {

    override fun candidateCount(memberId: Long): Long {
        return jdbcClient.sql(CANDIDATE_COUNT_SQL)
            .param("memberId", memberId)
            .query { rs, _ -> rs.getLong(1) }
            .single()
    }

    override fun otherCandidateCount(memberId: Long): Long {
        return jdbcClient.sql(OTHER_CANDIDATE_COUNT_SQL)
            .param("memberId", memberId)
            .query { rs, _ -> rs.getLong(1) }
            .single()
    }

    /**
     * 1. 내가 초대한 멤버 중에서 후보자 목록 조회
     * 2. 후보자 목록 중에 프로필을 등록한 후보자 목록 조회
     * 3. 후보자 프로필 이미지 조회
     * 4. 주선 진행중인 정보 조회
     */
    override fun findCandidates(memberId: Long): MatchCandidates {
        // 내 후보자 정보 조회
        val memberRelations = memberRelations(memberId)
        val relationMap = memberRelations.associateBy(MemberRelationDto::inviteeId, MemberRelationDto::relationType)
        val matchCandidates = matchCandidates(memberRelations.map(MemberRelationDto::inviteeId))
        val matchCandidateMap: Map<Long, MatchCandidateDto> = matchCandidates.associateBy(MatchCandidateDto::id)
        val matchCandidateIds = matchCandidateMap.keys
        val matchCandidateImages = matchCandidateImages(matchCandidateIds)
        val matchCandidateImageMap = matchCandidateImages.groupBy(MatchCandidateImageDto::matchCandidateId)
        val members = members(matchCandidates.map(MatchCandidateDto::memberId))
        val memberMap: Map<Long, MemberDto> = members.associateBy(MemberDto::id)

        // 후보자 주선 과정 정보 조회, 주선이 있는 사람들의 경우, 상대방의 프로필도 보여줘야 한다.
        // 우측에 있는 사용자가 내 후보자임을 확정한 정책이다.
        val leftArrangement = arrangementsByLeft(matchCandidates.map(MatchCandidateDto::memberId))
        val leftArrangementGroup = leftArrangement.groupBy(ArrangementDto::leftCandidateMemberId)
        val rightArrangement = arrangementsByRight(matchCandidates.map(MatchCandidateDto::memberId))
        val rightArrangementGroup = rightArrangement.groupBy(ArrangementDto::rightCandidateMemberId)
        val arrangementMap: Map<Long, List<ArrangementDto>> = (leftArrangementGroup.asSequence() + rightArrangementGroup.asSequence())
            .groupBy({ it.key }, { it.value })
            .mapValues { (_, value) -> value.flatten() }

        // 상대 후보자 조회
        val daterIds = arrangementMap.entries.flatMap { it.value.map { arrangement -> arrangement.otherCandidateId(it.key) } }
        val daters = members(daterIds)
        val daterMap = daters.associateBy(MemberDto::id)
        val daterImages = matchCandidateImages(daterIds.toSet())
        val daterImageMap = daterImages.groupBy(MatchCandidateImageDto::matchCandidateId)

        matchCandidates.map { matchCandidate ->
            MatchCandidate(
                image = matchCandidateImageMap[matchCandidate.id]?.firstOrNull { it.profileOrder == 1 }?.imageUrl  ?: "",
                name = memberMap[matchCandidate.memberId]!!.name,
                relation = relationMap[matchCandidate.memberId]!!,
                isMatchingPaused = matchCandidate.isMatchAgreed.not(),
                hasProfile = matchCandidate.hasProfile(),
                arrangements = arrangementMap[matchCandidate.memberId]?.map { arrangement ->
                    val otherCandidateId = arrangement.otherCandidateId(matchCandidate.memberId)
                    val otherCandidate = daterMap[otherCandidateId]!!
                    val otherCandidateImages = daterImageMap[otherCandidateId]!!
                    val otherCandidateImageUrl = otherCandidateImages.firstOrNull { it.profileOrder == 1 }?.imageUrl ?: ""
                    ArrangementInfo(
                        image = otherCandidateImageUrl,
                        name = otherCandidate.name,
                        arrangementStatus = arrangement.arrangementStatus
                    ) } ?: emptyList()
            )
        }.let { return MatchCandidates(it) }
    }

    private fun members(map: List<Long>): List<MemberDto> {
        if(map.isEmpty()) {
            return emptyList()
        }
        return jdbcClient.sql("SELECT id, name, email FROM member WHERE id IN (:ids)")
            .param("ids", map)
            .query(MemberDtoMapper())
            .list()
    }

    private fun arrangements(memberId: Long): MutableList<ArrangementDto> {
        return jdbcClient.sql("SELECT * FROM arrangements WHERE match_maker_member_id = :match_maker_member_ids")
            .param("match_maker_member_ids", memberId)
            .query(ArrangementDtoMapper())
            .list()
    }

    private fun arrangementsByLeft(candidateIds: List<Long>): MutableList<ArrangementDto> {
        if(candidateIds.isEmpty()) {
            return mutableListOf()
        }
        return jdbcClient.sql("SELECT * FROM arrangements WHERE left_candidate_member_id IN (:candidateIds)")
            .param("candidateIds", candidateIds)
            .query(ArrangementDtoMapper())
            .list()
    }

    private fun arrangementsByRight(candidateIds: List<Long>): MutableList<ArrangementDto> {
        if(candidateIds.isEmpty()) {
            return mutableListOf()
        }
        return jdbcClient.sql("SELECT * FROM arrangements WHERE right_candidate_member_id IN (:candidateIds)")
            .param("candidateIds", candidateIds)
            .query(ArrangementDtoMapper())
            .list()
    }


    private fun matchCandidateImages(matchCandidateIds: Set<Long>): MutableList<MatchCandidateImageDto> {
        if (matchCandidateIds.isEmpty()) {
            return mutableListOf()
        }
        return jdbcClient.sql("SELECT * FROM match_candidate_images WHERE match_candidate_id = :ids")
            .param("ids", matchCandidateIds)
            .query(MatchCandidateImageDtoMapper())
            .list()
    }


    private fun matchCandidates(invitees: List<Long>): MutableList<MatchCandidateDto>  {
        if (invitees.isEmpty()) {
            return mutableListOf()
        }
        return jdbcClient.sql("SELECT * FROM match_candidate WHERE member_id = :ids")
            .param("ids", invitees)
            .query(MatchCandidateDtoMapper())
            .list()
    }


    private fun memberRelations(memberId: Long): MutableList<MemberRelationDto> =
        jdbcClient.sql("SELECT * FROM member_relations WHERE inviter_id = :inviterId")
            .param("inviterId", memberId)
            .query(MemberRelationDtoMapper())
            .list()

    override fun findOtherCandidates(memberId: Long): OtherMatchCandidates {
        throw NotImplementedError()
    }
}
