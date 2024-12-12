package com.official.cufitapi.domain.infrastructure.entity

import com.official.cufitapi.domain.enums.MatchMakerCandidateRelationType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class MakerCandidateRelation(
    @Id @GeneratedValue
    val id: Long? = null,
    val makerId: Long,
    val candidateId: Long,
    val relationType: MatchMakerCandidateRelationType
) {

}