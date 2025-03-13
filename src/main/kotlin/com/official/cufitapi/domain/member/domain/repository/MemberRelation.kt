package com.official.cufitapi.domain.member.domain.repository

import com.official.cufitapi.domain.member.domain.MemberRelation

interface MemberRelationRepository {
    fun save(memberRelation: MemberRelation): MemberRelation
}
