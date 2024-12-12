package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.enums.MBTILetter
import com.official.cufitapi.domain.infrastructure.entity.MatchCandidate
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MatchCandidateJpaRepository : JpaRepository<MatchCandidate, Long> {

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

    data class CandidateMember(
        val memberId: Long,
        val name: String,
        val age: Int
    ) {

    }


    data class CandidateInfo(
        val candidateId: Long,
        val images: List<Image>,
        val name: String,
        val age: String,
        @Schema(description = "주선자와의 관계", example = "직장동료")
        val relation: String,
        val matchMakerName: String,
        val mbti: List<MBTILetter>,
        val height: Int,
        val station: String,
        val job: String
    )

    data class Image(
        val imageUrl: String ,
        val profileOrder: Int
    ) {

    }

}