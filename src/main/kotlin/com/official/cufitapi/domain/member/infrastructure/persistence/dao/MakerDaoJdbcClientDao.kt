package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.domain.vo.IdealHeightUnit
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.ArrangementDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.ArrangementInfo
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.Candidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateImageDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.Candidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberRelationDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcArrangementDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcCandidateDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcCandidateImageDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcMemberDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcMemberRelationDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.CANDIDATE_COUNT_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.OTHER_CANDIDATE_COUNT_SQL
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Component

@Component
class MakerDaoJdbcClientDao(
    private val jdbcClient: JdbcClient,
) : MakerDao {

    override fun candidateCount(memberId: Long): Long =
        jdbcClient.sql(CANDIDATE_COUNT_SQL)
            .param("memberId", memberId)
            .query(JdbcCandidateDtoMapper())
            .list()
            .count { it.hasProfile() }
            .toLong()

    override fun otherCandidateCount(memberId: Long): Long =
        jdbcClient.sql(OTHER_CANDIDATE_COUNT_SQL)
            .param("memberId", memberId)
            .query(JdbcCandidateDtoMapper())
            .list()
            .count { it.hasProfile() }
            .toLong()

    /**
     * 1. 내가 초대한 멤버 중에서 후보자 목록 조회
     * 2. 후보자 목록 중에 프로필을 등록한 후보자 목록 조회
     * 3. 후보자 프로필 이미지 조회
     * 4. 주선 진행중인 정보 조회
     */
    override fun findCandidates(memberId: Long): Candidates {
        // 내 후보자 정보 조회
        val memberRelations = memberRelations(memberId)
        val relationMap = memberRelations.associateBy(MemberRelationDto::inviteeId, MemberRelationDto::relationType)
        val candidates = candidates(memberRelations.map(MemberRelationDto::inviteeId))
        val candidateMap: Map<Long, CandidateDto> = candidates.associateBy(CandidateDto::id)
        val candidateIds = candidateMap.keys
        val candidateImages = candidateImages(candidateIds)
        val candidateImageMap = candidateImages.groupBy(CandidateImageDto::candidateId)
        val members = members(candidates.map(CandidateDto::memberId))
        val memberMap: Map<Long, MemberDto> = members.associateBy(MemberDto::id)

        // 후보자 주선 과정 정보 조회, 주선이 있는 사람들의 경우, 상대방의 프로필도 보여줘야 한다.
        // 우측에 있는 사용자가 내 후보자임을 확정한 정책이다.
        val leftArrangement = arrangementsByLeft(candidates.map(CandidateDto::memberId))
        val leftArrangementGroup = leftArrangement.groupBy(ArrangementDto::leftCandidateMemberId)
        val rightArrangement = arrangementsByRight(candidates.map(CandidateDto::memberId))
        val rightArrangementGroup = rightArrangement.groupBy(ArrangementDto::rightCandidateMemberId)
        val arrangementMap: Map<Long, List<ArrangementDto>> =
            (leftArrangementGroup.asSequence() + rightArrangementGroup.asSequence())
                .groupBy({ it.key }, { it.value })
                .mapValues { (_, value) -> value.flatten() }

        // 상대 후보자 조회
        val daterIds =
            arrangementMap.entries.flatMap { it.value.map { arrangement -> arrangement.otherCandidateId(it.key) } }
        val daters = members(daterIds)
        val daterMap = daters.associateBy(MemberDto::id)
        val daterImages = candidateImages(daterIds.toSet())
        val daterImageMap = daterImages.groupBy(CandidateImageDto::candidateId)

        candidates.map { candidate ->
            Candidate(
                image = candidateImageMap[candidate.id]?.firstOrNull { it.profileOrder == 1 }?.imageUrl ?: "",
                name = memberMap[candidate.memberId]!!.name,
                relation = relationMap[candidate.memberId]!!,
                isMatchingPaused = candidate.isMatchAgreed.not(),
                hasProfile = candidate.hasProfile(),
                arrangements = arrangementMap[candidate.memberId]?.map { arrangement ->
                    val otherCandidateId = arrangement.otherCandidateId(candidate.memberId)
                    val otherCandidate = daterMap[otherCandidateId]!!
                    val otherCandidateImages = daterImageMap[otherCandidateId]!!
                    val otherCandidateImageUrl =
                        otherCandidateImages.firstOrNull { it.profileOrder == 1 }?.imageUrl ?: ""
                    ArrangementInfo(
                        image = otherCandidateImageUrl,
                        name = otherCandidate.name,
                        arrangementStatus = arrangement.arrangementStatus
                    )
                } ?: emptyList()
            )
        }.let { return Candidates(it) }
    }

    override fun findOtherCandidates(memberId: Long): OtherCandidates {
        val memberRelations = memberRelationsByNotMemberId(memberId)
        val relationMap = memberRelations.associateBy(MemberRelationDto::inviteeId, MemberRelationDto::relationType)
        val candidates =
            candidates(memberRelations.map(MemberRelationDto::inviteeId)).filter(CandidateDto::hasProfile)
        val candidateMap = candidates.associateBy(CandidateDto::id)
        val candidateIds = candidateMap.keys
        val candidateImages = candidateImages(candidateIds)
        val candidateImageMap = candidateImages.groupBy(CandidateImageDto::candidateId)
        val members = members(candidates.map(CandidateDto::memberId))
        val memberMap: Map<Long, MemberDto> = members.associateBy(MemberDto::id)

        candidates.map { candidate ->
            OtherCandidate(
                id = candidate.id,
                images = candidateImageMap[candidate.id]?.map { CandidateImage(it.imageUrl, it.profileOrder) }
                    ?: emptyList(),
                name = memberMap[candidate.memberId]!!.name,
                yearOfBirth = candidate?.yearOfBirth!!,
                mbti = candidate?.mbti!!,
                height = candidate?.height!!,
                station = candidate?.station!!,
                job = candidate?.job!!,
                relation = relationMap[candidate.memberId]!!,
                makerName = memberMap[candidate.memberId]?.name!!,
                idealHeightRange = mapToList(candidate),
                idealAgeRange = mapToString(candidate),
                idealMbti = mapToMbtiList(candidate),
            )
        }.let { return OtherCandidates(it) }
    }

    private fun mapToMbtiList(
        candidate: CandidateDto,
    ): List<String> {
        val idealMbti = candidate?.idealMbti!!
        return idealMbti.split(",")
    }

    private fun mapToString(
        candidate: CandidateDto,
    ): List<String> {
        val idealAgeRange = candidate?.idealAgeRange!!
        return idealAgeRange.split("-")
    }

    private fun mapToList(
        candidate: CandidateDto,
    ): List<Int> {
        val idealHeightRange = candidate?.idealHeightRange!!
        return idealHeightRange.split(",")
            .map { it.trim() }
            .map(IdealHeightUnit.Companion::from)
            .map(IdealHeightUnit::value)
    }

    private fun members(map: List<Long>): List<MemberDto> {
        if (map.isEmpty()) {
            return emptyList()
        }
        return jdbcClient.sql("SELECT id, name, email FROM member WHERE id IN (:ids)")
            .param("ids", map)
            .query(JdbcMemberDtoMapper())
            .list()
    }

    private fun arrangements(memberId: Long): MutableList<ArrangementDto> {
        return jdbcClient.sql("SELECT * FROM arrangements WHERE maker_member_id = :maker_member_ids")
            .param("maker_member_ids", memberId)
            .query(JdbcArrangementDtoMapper())
            .list()
    }

    private fun arrangementsByLeft(candidateIds: List<Long>): MutableList<ArrangementDto> {
        if (candidateIds.isEmpty()) {
            return mutableListOf()
        }
        return jdbcClient.sql("SELECT * FROM arrangements WHERE left_candidate_member_id IN (:candidateIds)")
            .param("candidateIds", candidateIds)
            .query(JdbcArrangementDtoMapper())
            .list()
    }

    private fun arrangementsByRight(candidateIds: List<Long>): MutableList<ArrangementDto> {
        if (candidateIds.isEmpty()) {
            return mutableListOf()
        }
        return jdbcClient.sql("SELECT * FROM arrangements WHERE right_candidate_member_id IN (:candidateIds)")
            .param("candidateIds", candidateIds)
            .query(JdbcArrangementDtoMapper())
            .list()
    }

    private fun candidateImages(candidateIds: Set<Long>): MutableList<CandidateImageDto> {
        if (candidateIds.isEmpty()) {
            return mutableListOf()
        }
        return jdbcClient.sql("SELECT * FROM candidate_images WHERE candidate_id IN(:ids)")
            .param("ids", candidateIds)
            .query(JdbcCandidateImageDtoMapper())
            .list()
    }

    private fun candidates(invitees: List<Long>): MutableList<CandidateDto> {
        if (invitees.isEmpty()) {
            return mutableListOf()
        }
        return jdbcClient.sql("SELECT * FROM candidates WHERE member_id IN(:ids)")
            .param("ids", invitees)
            .query(JdbcCandidateDtoMapper())
            .list()
    }

    private fun memberRelations(memberId: Long): MutableList<MemberRelationDto> =
        jdbcClient.sql("SELECT * FROM member_relations WHERE inviter_id = :inviterId")
            .param("inviterId", memberId)
            .query(JdbcMemberRelationDtoMapper())
            .list()

    private fun memberRelationsByNotMemberId(memberId: Long): MutableList<MemberRelationDto> =
        jdbcClient.sql("SELECT * FROM member_relations WHERE inviter_id != :inviterId")
            .param("inviterId", memberId)
            .query(JdbcMemberRelationDtoMapper())
            .list()
}
