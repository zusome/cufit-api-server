package com.official.cufitapi.domain.member.domain.repository

import com.official.cufitapi.domain.member.domain.Member

interface MemberRepository {
    fun update(member: Member) : Member
}