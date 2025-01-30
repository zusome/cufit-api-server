package com.official.cufitapi.domain.member.infrastructure.persistence

import com.official.cufitapi.domain.member.domain.vo.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "match_candidate")
class MatchCandidateEntity(

    @JoinColumn(name = "member_id")
    @OneToOne
    var member: MemberEntity,

    @Column(name = "is_matching_agreed", unique = false, nullable = false)
    @Comment("매칭동의여부")
    var isMatchingAgreed: Boolean = true,

    @Column(name = "ideal_mbti", unique = false, nullable = true)
    @Comment("이상형 MBTI")
    var idealMbti: String? = null,

    @Column(name = "ideal_age_range", unique = false, nullable = true)
    @Comment("이상형 나이대")
    var idealAgeRange: String? = null,

    @Column(name = "ideal_height_range", unique = false, nullable = true)
    @Comment("이상형 키대")
    var idealHeightRange: String? = null,

    @Column(name = "mbti", unique = false, nullable = true)
    @Comment("MBTI")
    var mbti: String? = null,

    @Column(name = "height", unique = false, nullable = true)
    @Comment("신장")
    var height: Int? = null,

    @Column(name = "station", unique = false, nullable = true)
    @Comment("지하철역")
    var station: String? = null,

    @Column(name = "job", unique = false, nullable = true)
    @Comment("직업")
    var job: String? = null,

    @Column(name = "year_of_birth", unique = false, nullable = true)
    @Comment("출생연도")
    var yearOfBirth: Int? = null,

    @Comment("성별")
    @Column(name = "gender", unique = false, nullable = true)
    @Enumerated(value = EnumType.STRING)
    var gender: Gender? = null,

    @Column(name = "phone_number", unique = false, nullable = true)
    @Comment("사용자 전화 번호")
    var phoneNumber: String? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {

    fun deactivateMatching() {
        isMatchingAgreed = false
    }

    fun hasProfile(): Boolean {
        return idealMbti != null || idealAgeRange != null || idealHeightRange != null || height != null || station != null || job != null || yearOfBirth != null
    }

    fun updateProfile(
        idealMbti: String,
        idealAgeRange: String,
        idealHeightRange: String,
        mbti: String,
        height: Int,
        station: String,
        job: String,
        yearOfBirth: Int,
        gender: Gender,
        phoneNumber: String
    ) {
        this.idealMbti = idealMbti
        this.idealAgeRange = idealAgeRange
        this.idealHeightRange = idealHeightRange
        this.mbti = mbti
        this.height = height
        this.station = station
        this.job = job
        this.yearOfBirth = yearOfBirth
        this.gender = gender
        this.phoneNumber = phoneNumber
    }

    fun isSameGender(other: MatchCandidateEntity): Boolean =
        this.gender == other.gender

    fun updateMatchingAgreement(isMatchingAgreed: Boolean) {
        this.isMatchingAgreed = isMatchingAgreed
    }
}
