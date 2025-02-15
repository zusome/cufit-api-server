package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.infrastructure.persistence.dto.ArrangementDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.ArrangementInfo
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidateDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidateImageDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberRelationDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherMatchCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.ArrangementDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.MatchCandidateDtoMapper
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.MatchCandidateImageDtoMapper
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
     * 4. ArrangeMet
     */
    override fun findCandidates(memberId: Long): MatchCandidates {
        throw NotImplementedError()

        val memberRelationDtos = jdbcClient.sql("SELECT * FROM member_relations WHERE inviter_id = :inviterId")
            .param("inviterId", memberId)
            .query(MemberRelationDtoMapper())
            .list()

        val relationMap: Map<Long, String> = memberRelationDtos
            .associateBy(MemberRelationDto::inviteeId, MemberRelationDto::relationType)

        val matchCandidateDtos = jdbcClient.sql("SELECT * FROM match_candidate WHERE member_id = :ids")
            .param("ids", memberRelationDtos.map(MemberRelationDto::inviteeId))
            .query(MatchCandidateDtoMapper())
            .list()
            .associateBy(MatchCandidateDto::id)

        val matchCandidateImageDtos = jdbcClient.sql("SELECT * FROM match_candidate_images WHERE match_candidate_id = :ids")
            .param("ids", matchCandidateDtos::keys)
            .query(MatchCandidateImageDtoMapper())
            .list()
            .associateBy(MatchCandidateImageDto::id)

        matchCandidateDtos.forEach { (id, matchCandidateDto) ->
            matchCandidateDto.images = matchCandidateImageDtos.filter { it.key == id }.values.toMutableList()
        }

        val arrangementDtos = jdbcClient.sql("SELECT * FROM arrangements WHERE match_maker_member_id = :ids")
            .param("ids", memberId)
            .query(ArrangementDtoMapper())
            .list()

        // 매칭이 있는 사람들의 경우, 상대방의 프로필도 보여줘야 한다.



        matchCandidateDtos.values.map { value ->
            MatchCandidate(
                image = value.images.first { it.profileOrder == 1 }.imageUrl,
                name = value.phoneNumber!!,
                relation = relationMap[value.memberId]!!,
                isMatchingPaused = value.isMatchAgreed.not(),
                hasProfile = value.hasProfile(),
                arrangements = arrangementDtos.filter {
                    it.leftCandidateMemberId == value.memberId || it.rightCandidateMemberId == value.memberId
                }.map { arrangementDto ->
                    arrangementDto.otherCandidateId(value.memberId)
                    val candidateId = if (arrangementDto.leftCandidateMemberId == value.memberId) {
                        arrangementDto.rightCandidateMemberId
                    } else {
                        arrangementDto.leftCandidateMemberId
                    }
                    val candidate = matchCandidateDtos[candidateId]!!
                    ArrangementInfo(
                        image = candidate.images.first { it.profileOrder == 1 }.imageUrl,
                        name = candidate.phoneNumber!!,
                        arrangementStatus = arrangementDto.arrangementStatus
                    )
                }
            )
        }
    }

    override fun findOtherCandidates(memberId: Long): OtherMatchCandidates {
        throw NotImplementedError()
    }
}
