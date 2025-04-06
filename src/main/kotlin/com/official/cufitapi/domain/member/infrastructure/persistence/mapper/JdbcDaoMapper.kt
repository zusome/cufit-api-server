package com.official.cufitapi.domain.member.infrastructure.persistence.mapper

import com.official.cufitapi.domain.member.infrastructure.persistence.dto.ArrangementDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidateDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidateImageDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberRelationDto
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class JdbcMemberRelationDtoMapper : RowMapper<MemberRelationDto> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MemberRelationDto =
        MemberRelationDto(
            inviterId = rs.getLong("inviter_id"),
            inviteeId = rs.getLong("invitee_id"),
            relationType = rs.getString("relation_type"),
            id = rs.getLong("id")
        )
}

class JdbcMatchCandidateDtoMapper : RowMapper<MatchCandidateDto> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MatchCandidateDto {
        return MatchCandidateDto(
            memberId = rs.getLong("member_id"),
            isMatchAgreed = rs.getBoolean("is_match_agreed"),
            idealMbti = rs.getString("ideal_mbti"),
            idealAgeRange = rs.getString("ideal_age_range"),
            idealHeightRange = rs.getString("ideal_height_range"),
            mbti = rs.getString("mbti"),
            height = rs.getInt("height"),
            station = rs.getString("station"),
            job = rs.getString("job"),
            yearOfBirth = rs.getInt("year_of_birth"),
            gender = rs.getString("gender"),
            phoneNumber = rs.getString("phone_number"),
            id = rs.getLong("id")
        )
    }
}

class JdbcMemberDtoMapper : RowMapper<MemberDto> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MemberDto {
        return MemberDto(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            email = rs.getString("email")
        )
    }
}

class JdbcMatchCandidateImageDtoMapper : RowMapper<MatchCandidateImageDto> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MatchCandidateImageDto {
        return MatchCandidateImageDto(
            imageUrl = rs.getString("image_url"),
            profileOrder = rs.getInt("profile_order"),
            matchCandidateId = rs.getLong("match_candidate_id"),
            seq = rs.getLong("id")
        )
    }
}

class JdbcArrangementDtoMapper : RowMapper<ArrangementDto> {
    override fun mapRow(rs: ResultSet, rowNum: Int): ArrangementDto {
        return ArrangementDto(
            makerMemberId = rs.getLong("match_maker_member_id"),
            leftCandidateMemberId = rs.getLong("left_candidate_member_id"),
            rightCandidateMemberId = rs.getLong("right_candidate_member_id"),
            arrangementStatus = rs.getString("status"),
            id = rs.getLong("id")
        )
    }
}
