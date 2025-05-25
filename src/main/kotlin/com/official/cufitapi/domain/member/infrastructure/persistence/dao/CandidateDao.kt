package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.common.tomorrow
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateMatchResultResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateMatchSuggestionResponse
import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.domain.vo.IdealHeightUnit
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateImageDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateProfileInfo
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberDto
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcCandidateDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcCandidateImageDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcMatchDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcMemberDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JdbcMemberRelationDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.CANDIDATE_BY_MEMBER_ID_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.CANDIDATE_IMAGE_BY_MEMBER_IDS_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.MATCHES_BY_LEFT_CANDIDATE_MEMBER_ID_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.MATCHES_BY_RIGHT_CANDIDATE_MEMBER_ID_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.MEMBER_RELATIONS_BY_INVITEE_ID
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.MEMBER_SQL
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Component
import java.time.ZoneId
import javax.sql.DataSource

@Component
class CandidateDao(
    dataSource: DataSource,
    private val jdbcClient: JdbcClient,
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
                city,
                district,
                job,
                year_of_birth,
                gender,
                phone_number,
                hobbies,
                smoke,
                drink
            FROM
                candidates
            WHERE
                member_id = :candidateId
        """
        sql.trimIndent()
        return namedParameterJdbcTemplate.queryForObject(sql, queryParameters, CandidateProfileInfoMapper())
            ?: throw RuntimeException("Candidate not found")
    }

    fun findMatchSuggestions(candidateMemberId: Long, startIndex: Long, limit: Long): List<CandidateMatchSuggestionResponse> {
        val candidate = candidate(candidateMemberId)
        val lefts = matchesByLeftCandidate(candidateMemberId)
        val leftToRightCandidateMatches = lefts
            .filter { it.matchStatus == "0" || it.matchStatus == "1" }
            .filter { !it.leftCandidateAgree }
            .toMutableList()
        val leftToRightTargetImagesMap =
            candidateImageMapByMemberIds(leftToRightCandidateMatches.map { it.rightCandidateMemberId })
        val leftCandidateResponses =
            candidatesMatchSuggestionResponse(leftToRightCandidateMatches, leftToRightTargetImagesMap, candidate)

        val rights = matchesByRightCandidate(candidateMemberId)
        val rightToLeftCandidateMatches = rights
            .filter { it.matchStatus == "0" || it.matchStatus == "1" }
            .filter { !it.rightCandidateAgree }
            .toMutableList()
        val rightToLeftTargetImagesMap =
            candidateImageMapByMemberIds(rightToLeftCandidateMatches.map { it.leftCandidateMemberId })
        val rightCandidateResponses =
            candidatesMatchSuggestionResponse(rightToLeftCandidateMatches, rightToLeftTargetImagesMap, candidate)
        return (leftCandidateResponses + rightCandidateResponses)
    }

    fun findMatchResult(candidateMemberId: Long, startIndex: Long, limit: Long): List<CandidateMatchResultResponse> {
        val candidate = candidate(candidateMemberId)
        val leftToRightCandidateMatches = matchesByLeftCandidate(candidateMemberId)
            .filter { it.matchStatus != "0" }
            .filter { it.leftCandidateAgree }
            .toMutableList()
        val leftToRightTargetImagesMap =
            candidateImageMapByMemberIds(leftToRightCandidateMatches.map { it.rightCandidateMemberId })
        val leftCandidateResponses =
            candidatesMatchResultResponse(leftToRightCandidateMatches, leftToRightTargetImagesMap, candidate)

        val rightToLeftCandidateMatches = matchesByRightCandidate(candidateMemberId)
            .filterNot { it.matchStatus == "0" }
            .filter { it.rightCandidateAgree }
            .toMutableList()
        val rightToLeftTargetImagesMap =
            candidateImageMapByMemberIds(rightToLeftCandidateMatches.map { it.leftCandidateMemberId })
        val rightCandidateResponses =
            candidatesMatchResultResponse(rightToLeftCandidateMatches, rightToLeftTargetImagesMap, candidate)
        return (leftCandidateResponses + rightCandidateResponses)
    }

    private fun candidatesMatchSuggestionResponse(
        matches: MutableList<MatchDto>,
        candidateImageMap: Map<Long, List<CandidateImageDto>>,
        candidate: CandidateDto,
    ): List<CandidateMatchSuggestionResponse> = matches.map {
        val matchMemberId = it.rightCandidateMemberId
        val matchMember = member(matchMemberId)
        val matchRelation = memberRelation(matchMemberId)
        val matchMakerName = member(matchRelation.inviterId).name
        val matchMakerRelation = matchRelation.relationType
        CandidateMatchSuggestionResponse(
            id = matchMember.id,
            images = candidateImageMap[matchMemberId]?.map { targetImage ->
                CandidateImage(targetImage.imageUrl, targetImage.profileOrder)
            } ?: emptyList(),
            name = matchMember.name,
            yearOfBirth = candidate.yearOfBirth!!,
            mbti = candidate.mbti.toString(),
            height = candidate.height!!,
            city = candidate.city!!,
            district = candidate.district!!,
            job = candidate.job!!,
            hobbies = candidate.hobbies?.split(",") ?: listOf(),
            smoke = candidate.smoke!!,
            drink = candidate.drink!!,
            idealHeightRange = mapToHeightRange(candidate),
            idealAgeRange = mapToIdealAgeRange(candidate),
            idealMbti = mapToMbtiList(candidate),
            makerRelation = matchMakerRelation,
            makerName = matchMakerName,
            matchId = it.id,
            expiredTime = it.createdDate.tomorrow().atZone(ZoneId.of("Asia/Seoul")).withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli(),
        )
    }

    private fun candidatesMatchResultResponse(
        matches: MutableList<MatchDto>,
        candidateImageMap: Map<Long, List<CandidateImageDto>>,
        candidate: CandidateDto,
    ): List<CandidateMatchResultResponse> = matches.map {
        val matchMemberId = it.rightCandidateMemberId
        val matchMember = member(matchMemberId)
        val matchRelation = memberRelation(matchMemberId)
        val matchMakerName = member(matchRelation.inviterId).name
        val matchMakerRelation = matchRelation.relationType

        // 만료 시간이 지나지 않았다면 핸드폰 번호를 안보이게 한다.
        val expiredTime = it.modifiedDate.tomorrow().atZone(ZoneId.of("Asia/Seoul")).withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        CandidateMatchResultResponse(
            id = matchMember.id,
            images = candidateImageMap[matchMemberId]?.map { targetImage ->
                CandidateImage(targetImage.imageUrl, targetImage.profileOrder)
            } ?: emptyList(),
            name = matchMember.name,
            yearOfBirth = candidate.yearOfBirth!!,
            mbti = candidate.mbti.toString(),
            height = candidate.height!!,
            city = candidate.city!!,
            district = candidate.district!!,
            job = candidate.job!!,
            hobbies = candidate.hobbies?.split(",") ?: listOf(),
            smoke = candidate.smoke!!,
            drink = candidate.drink!!,
            idealHeightRange = mapToHeightRange(candidate),
            idealAgeRange = mapToIdealAgeRange(candidate),
            idealMbti = mapToMbtiList(candidate),
            makerRelation = matchMakerRelation,
            makerName = matchMakerName,
            phoneNumber = if (expiredTime > System.currentTimeMillis()) { candidate.phoneNumber } else { null },
            matchStatus = it.matchStatus,
            matchId = it.id,
            expiredTime = expiredTime,
        )
    }

    private fun matchesByRightCandidate(candidateMemberId: Long): MutableList<MatchDto> =
        jdbcClient.sql(MATCHES_BY_RIGHT_CANDIDATE_MEMBER_ID_SQL)
            .param("rightMemberId", candidateMemberId)
            .query(JdbcMatchDtoMapper())
            .list()

    private fun matchesByLeftCandidate(candidateMemberId: Long): MutableList<MatchDto> =
        jdbcClient.sql(MATCHES_BY_LEFT_CANDIDATE_MEMBER_ID_SQL)
            .param("leftMemberId", candidateMemberId)
            .query(JdbcMatchDtoMapper())
            .list()

    private fun member(memberId: Long): MemberDto =
        jdbcClient.sql(MEMBER_SQL)
            .param("id", memberId)
            .query(JdbcMemberDtoMapper())
            .single()

    private fun memberRelation(candidateMemberId: Long) = jdbcClient.sql(MEMBER_RELATIONS_BY_INVITEE_ID)
        .param("inviteeId", candidateMemberId)
        .query(JdbcMemberRelationDtoMapper())
        .single()

    private fun candidate(candidateMemberId: Long) = jdbcClient.sql(CANDIDATE_BY_MEMBER_ID_SQL)
        .param("memberId", candidateMemberId)
        .query(JdbcCandidateDtoMapper())
        .single()

    private fun candidateImageMapByMemberIds(leftToRightOtherTargets: List<Long>): Map<Long, List<CandidateImageDto>> {
        if(leftToRightOtherTargets.isEmpty()) {
            return emptyMap()
        }
        return jdbcClient.sql(CANDIDATE_IMAGE_BY_MEMBER_IDS_SQL)
            .param("ids", leftToRightOtherTargets)
            .query(JdbcCandidateImageDtoMapper())
            .list()
            .groupBy(CandidateImageDto::memberId)
    }

    private fun mapToHobbies(
        candidate: CandidateDto,
    ): List<String> {
        val hobbies = candidate?.hobbies!!
        return hobbies.split(",")
    }

    private fun mapToMbtiList(
        candidate: CandidateDto,
    ): List<String> {
        val idealMbti = candidate?.idealMbti!!
        return idealMbti.split(",")
    }

    private fun mapToIdealAgeRange(
        candidate: CandidateDto,
    ): List<String> {
        val idealAgeRange = candidate?.idealAgeRange!!
        return idealAgeRange.split("-")
    }

    private fun mapToHeightRange(
        candidate: CandidateDto,
    ): List<Int> {
        val idealHeightRange = candidate?.idealHeightRange!!
        return idealHeightRange.split(",")
            .map { it.trim() }
            .map(IdealHeightUnit.Companion::from)
            .map(IdealHeightUnit::value)
    }
}

