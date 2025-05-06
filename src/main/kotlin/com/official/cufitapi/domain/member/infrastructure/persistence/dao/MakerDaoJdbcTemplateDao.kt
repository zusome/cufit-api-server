package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchInfo
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.Candidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateDetailInfo
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
                    matches = listOf(),
                    hasProfile = true,
                    isMatchingPaused = false,
                    candidateDetailInfo = CandidateDetailInfo(
                        id = 1L,
                        images = listOf(
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 1),
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 2)
                        ),
                        name = "김철수",
                        yearOfBirth = 1997,
                        makerRelation = "직장동료",
                        makerName = "홍길동",
                        mbti = "ESFP",
                        height = 165,
                        city = "서울특별시",
                        district = "강남구",
                        job = "디자이너",
                        hobbies = listOf("보드게임", "노래부르기", "사진 촬영"),
                        smoke = 0,
                        drink = 0,
                        idealHeightRange = listOf(170, 190),
                        idealAgeRange = listOf("연상", "연하", "동갑"),
                        idealMbti = listOf("외향적", "직관적", "계획형", "즉흥형")
                    )
                ),
                Candidate(
                    image = "https://cataas.com/cat",
                    name = "홍길동",
                    relation = "직장동료",
                    matches = listOf(
                        MatchInfo(
                            image = "https://cataas.com/cat",
                            name = "장충동",
                            matchStatus = "성공"
                        ),
                        MatchInfo(
                            image = "https://cataas.com/cat",
                            name = "박혁거세",
                            matchStatus = "실패"
                        ),
                        MatchInfo(
                            image = "https://cataas.com/cat",
                            name = "박동충",
                            matchStatus = "대기"
                        )
                    ),
                    hasProfile = true,
                    isMatchingPaused = false,
                    candidateDetailInfo = CandidateDetailInfo(
                        id = 2L,
                        images = listOf(
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 1),
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 2)
                        ),
                        name = "홍길동",
                        yearOfBirth = 1997,
                        makerRelation = "직장동료",
                        makerName = "김철수",
                        mbti = "ESFP",
                        height = 165,
                        city = "서울특별시",
                        district = "강남구",
                        job = "디자이너",
                        hobbies = listOf("보드게임", "노래부르기", "사진 촬영"),
                        smoke = 0,
                        drink = 0,
                        idealHeightRange = listOf(170, 190),
                        idealAgeRange = listOf("연상", "연하", "동갑"),
                        idealMbti = listOf("외향적", "직관적", "계획형", "즉흥형")
                    )
                ),
                Candidate(
                    image = "https://cataas.com/cat",
                    name = "박혁거세",
                    relation = "직장동료",
                    matches = listOf(),
                    hasProfile = false,
                    isMatchingPaused = false,
                    candidateDetailInfo = CandidateDetailInfo(
                        id = 3L,
                        images = listOf(
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 1),
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 2)
                        ),
                        name = "박혁거세",
                        yearOfBirth = 1997,
                        makerRelation = "직장동료",
                        makerName = "김철수",
                        mbti = "ESFP",
                        height = 165,
                        city = "서울특별시",
                        district = "강남구",
                        job = "디자이너",
                        hobbies = listOf("보드게임", "노래부르기", "사��� 촬영"),
                        smoke = 0,
                        drink = 0,
                        idealHeightRange = listOf(170, 190),
                        idealAgeRange = listOf("연상", "연하", "동갑"),
                        idealMbti = listOf("외향적", "직관적", "계획형", "즉흥형")
                    )
                ),
                Candidate(
                    image = "https://cataas.com/cat",
                    name = "김민지",
                    relation = "친구",
                    matches = listOf(),
                    hasProfile = true,
                    isMatchingPaused = true,
                    candidateDetailInfo = CandidateDetailInfo(
                        id = 4L,
                        images = listOf(
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 1),
                            CandidateImage(imageUrl = "https://cataas.com/cat", profileOrder = 2)
                        ),
                        name = "김민지",
                        yearOfBirth = 1997,
                        makerRelation = "직장동료",
                        makerName = "김철수",
                        mbti = "ESFP",
                        height = 165,
                        city = "서울특별시",
                        district = "강남구",
                        job = "디자이너",
                        hobbies = listOf("보드게임", "노래부르기", "사진 촬영"),
                        smoke = 0,
                        drink = 0,
                        idealHeightRange = listOf(170, 190),
                        idealAgeRange = listOf("연상", "연하", "동갑"),
                        idealMbti = listOf("외향적", "직관적", "계획형", "즉흥형")
                    )
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
                    city = "서울특별시",
                    district = "강남구",
                    job = "디자이너",
                    relation = "직장동료",
                    makerName = "김철수",
                    idealHeightRange = listOf(170, 190),
                    idealAgeRange = listOf("연상", "연하", "동갑"),
                    idealMbti = listOf("외향적", "직관적", "계획형", "즉흥형"),
                    hobbies = listOf("보드게임", "노래부르기", "사진 촬영"),
                    smoke = 0,
                    drink = 0
                )
            )
        )
    }
}
