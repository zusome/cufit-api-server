package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.ArrangementInfo
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.Candidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.Candidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.CANDIDATE_COUNT_SQL
import com.official.cufitapi.domain.member.infrastructure.persistence.sql.MemberSqlConstant.OTHER_CANDIDATE_COUNT_SQL
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class MakerDaoJdbcTemplateDao(
    private val dataSource: DataSource,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource),
) : MakerDao {

    override fun otherCandidateCount(memberId: Long): Long {
        return namedParameterJdbcTemplate.queryForObject(
            OTHER_CANDIDATE_COUNT_SQL,
            mapOf("memberId" to memberId),
            Long::class.java
        ) ?: throw RuntimeException("Candidate not found")
    }

    override fun candidateCount(memberId: Long): Long {
        return namedParameterJdbcTemplate.queryForObject(
            CANDIDATE_COUNT_SQL,
            mapOf("memberId" to memberId),
            Long::class.java
        ) ?: throw RuntimeException("Candidate not found")
    }

    override fun findCandidates(memberId: Long): Candidates {
        return Candidates(
            listOf(
                Candidate(
                    image = "https://cataas.com/cat",
                    name = "김철수",
                    relation = "친구",
                    arrangements = listOf(),
                    hasProfile = true,
                    isMatchingPaused = false
                ),
                Candidate(
                    image = "https://cataas.com/cat",
                    name = "홍길동",
                    relation = "직장동료",
                    arrangements = listOf(
                        ArrangementInfo(
                            image = "https://cataas.com/cat",
                            name = "장충동",
                            arrangementStatus = "성공"
                        ),
                        ArrangementInfo(
                            image = "https://cataas.com/cat",
                            name = "박혁거세",
                            arrangementStatus = "실패"
                        ),
                        ArrangementInfo(
                            image = "https://cataas.com/cat",
                            name = "박동충",
                            arrangementStatus = "대기"
                        )
                    ),
                    hasProfile = true,
                    isMatchingPaused = false
                ),
                Candidate(
                    image = "https://cataas.com/cat",
                    name = "박혁거세",
                    relation = "직장동료",
                    arrangements = listOf(),
                    hasProfile = false,
                    isMatchingPaused = false
                ),
                Candidate(
                    image = "https://cataas.com/cat",
                    name = "김민지",
                    relation = "친구",
                    arrangements = listOf(),
                    hasProfile = true,
                    isMatchingPaused = true
                )
            )
        )
    }

    override fun findOtherCandidates(memberId: Long): OtherCandidates {
        return OtherCandidates(
            listOf(
                OtherCandidate(
                    id = 1L,
                    images = listOf(
                        CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 1),
                        CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 2)
                    ),
                    name = "홍길동",
                    yearOfBirth = 1997,
                    mbti = "ESFP",
                    height = 165,
                    station = "정자역",
                    job = "디자이너",
                    relation = "직장동료",
                    makerName = "김철수",
                    idealHeightRange = listOf(170, 190),
                    idealAgeRange = listOf("연상", "연하", "동갑"),
                    idealMbti = listOf("외향적", "직관적", "계획형", "즉흥형")
                )
            )
        )
    }
}
