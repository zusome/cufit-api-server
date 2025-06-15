package com.official.cufitapi.domain.member.infrastructure.persistence.mapper

import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateDto
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateImageDto
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

class JdbcCandidateDtoMapper : RowMapper<CandidateDto> {
    override fun mapRow(rs: ResultSet, rowNum: Int): CandidateDto {
        return CandidateDto(
            memberId = rs.getLong("member_id"),
            isMatchPaused = rs.getBoolean("is_match_paused"),
            idealMbti = rs.getString("ideal_mbti"),
            idealAgeRange = rs.getString("ideal_age_range"),
            idealHeightRange = rs.getString("ideal_height_range"),
            mbti = rs.getString("mbti"),
            height = rs.getInt("height"),
            city = rs.getString("city"),
            district = rs.getString("district"),
            job = rs.getString("job"),
            yearOfBirth = rs.getInt("year_of_birth"),
            gender = rs.getString("gender"),
            phoneNumber = rs.getString("phone_number"),
            hobbies = rs.getString("hobbies"),
            smoke = rs.getInt("smoke"),
            drink = rs.getInt("drink"),
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

class JdbcCandidateImageDtoMapper : RowMapper<CandidateImageDto> {
    override fun mapRow(rs: ResultSet, rowNum: Int): CandidateImageDto {
        return CandidateImageDto(
            imageUrl = rs.getString("image_url"),
            profileOrder = rs.getInt("profile_order"),
            candidateId = rs.getLong("candidate_id"),
            memberId = rs.getLong("member_id"),
            seq = rs.getLong("id")
        )
    }
}

class JdbcMatchDtoMapper : RowMapper<MatchDto> {
    override fun mapRow(rs: ResultSet, rowNum: Int): MatchDto {
        return MatchDto(
            makerMemberId = rs.getLong("maker_member_id"),
            leftCandidateMemberId = rs.getLong("left_candidate_member_id"),
            leftCandidateAgree = rs.getBoolean("left_candidate_agree"),
            rightCandidateMemberId = rs.getLong("right_candidate_member_id"),
            rightCandidateAgree = rs.getBoolean("right_candidate_agree"),
            matchStatus = rs.getString("status"),
            createdDate = rs.getTimestamp("created_date").toLocalDateTime(),
            modifiedDate = rs.getTimestamp("modified_date").toLocalDateTime(),
            id = rs.getLong("id")
        )
    }
}
