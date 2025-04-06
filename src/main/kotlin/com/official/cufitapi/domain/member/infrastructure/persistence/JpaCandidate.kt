package com.official.cufitapi.domain.member.infrastructure.persistence

import com.official.cufitapi.domain.member.domain.vo.Gender
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "candidates")
class JpaCandidate(

    @Column(name = "member_id", unique = false, nullable = false)
    var memberId: Long,

    @Column(name = "is_match_agreed", unique = false, nullable = false)
    @Comment("매칭동의여부")
    var isMatchAgreed: Boolean = true,

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

    @Column(name = "city", unique = false, nullable = true)
    @Comment("도시")
    var city: String? = null,

    @Column(name = "district", unique = false, nullable = true)
    @Comment("구역")
    var district: String? = null,

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

    @Column(name = "hobbies", unique = false, nullable = true)
    @Comment("취미")
    var hobbies: String? = null,

    @Column(name = "smoke", unique = false, nullable = true)
    @Comment("흡연")
    var smoke: Int? = null,

    @Column(name = "drink", unique = false, nullable = true)
    @Comment("음주")
    var drink: Int? = null,

    @OneToMany(mappedBy = "jpaCandidate", orphanRemoval = true, cascade = [CascadeType.ALL])
    @Comment("매칭 후보자 이미지")
    var images: MutableList<JpaCandidateImage> = mutableListOf(),

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    init {
        images.forEach { it.jpaCandidate = this }
    }

    fun isSameGender(other: JpaCandidate): Boolean =
        this.gender == other.gender
}
