package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.enums.MBTILetter
import com.official.cufitapi.domain.infrastructure.entity.MatchCandidate
import com.official.cufitapi.domain.infrastructure.repository.dto.candidate.CandidateInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchCandidateJpaRepository : JpaRepository<MatchCandidate, Long> {

    @Query(
        """
            SELECT 
            mc.id,
            mc.member_id as memberId,
            mc.ideal_mbti as idealMbti,
            mc.ideal_age_range as idealAgeRange,
            mc.ideal_height_range as idealHeightRange,
            mc.height,
            mc.station,
            mc.job,
            m.name,
            m.email,
            m.gender
            FROM match_candidate mc
            JOIN member m ON mc.member_id = m.id
        """,
        nativeQuery = true
    )
    fun findMemberByMatchCandidateId(@Param("id") candidateId: Long): MemberInfoByCandidateIdDto?

    data class MemberInfoByCandidateIdDto(
        val id: Long,
        val memberId: Long,
        val name: String,
        val email: String,
        val idealMbti: MBTILetter,
        val idealAgeRange: String,
        val idealHeightRange: String,
        val height: Int,
        val station: String,
        val job: String,
        val gender: String
    )

    @Query(
        value = """
            SELECT 
                mc.member_id as memberId,
                mc.ideal_mbti as idealMbti,
                mc.ideal_age_range as idealAgeRange,
                mc.ideal_height_range as idealHeightRange,
                mc.height,
                mc.station,
                mc.job,
                m.name,
                m.email,
                mpi.image_url as imageUrl,
                mpi.profile_order as profileOrder
            FROM 
                match_candidate mc
            JOIN 
                member m ON mc.member_id = m.id
            JOIN 
                member_profile_image mpi ON mpi.member_id = m.id
        """,
        nativeQuery = true
    )
    fun findAllCandidatesByMatchMaker(): List<CandidateInfo>


    @Query("""
        SELECT COUNT(e) FROM match_candidate e 
                WHERE e.match_maker_id = :matchMakerId
                AND e.sender_id = :senderId
                AND DATE(e.created_date) = CURRENT_DATE
        """,
        nativeQuery = true
    )
    fun countByMatchMakerIdAndSenderIdAndCreatedDate(
        @Param("matchMakerId") matchMakerId: Long?,
        @Param("senderId") senderId: Long?
    ): Long

}