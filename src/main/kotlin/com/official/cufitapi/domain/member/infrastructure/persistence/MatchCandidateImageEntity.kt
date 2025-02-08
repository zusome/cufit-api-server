package com.official.cufitapi.domain.member.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "match_candidate_images")
@Entity
class MatchCandidateImageEntity(
    @Column(name = "image_url", unique = false, nullable = false)
    var imageUrl: String,

    @Column(name = "profile_order", unique = false, nullable = false)
    var profileOrder: Int,

    @ManyToOne
    @JoinColumn(name = "match_candidate_id")
    var matchCandidateEntity: MatchCandidateEntity? = null,

    @Id @GeneratedValue
    var id: Long? = null,
) {

}
