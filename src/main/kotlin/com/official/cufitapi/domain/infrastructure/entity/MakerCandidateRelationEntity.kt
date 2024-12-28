package com.official.cufitapi.domain.infrastructure.entity

import com.official.cufitapi.domain.enums.MatchMakerCandidateRelationType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

/*
   주선자 - 후보자 관계 Table
 */
@Entity
@Table(name = "match_candidate_relation")
class MakerCandidateRelationEntity(
    @Id @GeneratedValue
    val id: Long? = null,
    val makerId: Long,
    val candidateId: Long,
    val relationType: MatchMakerCandidateRelationType
) {

}