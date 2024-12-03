package com.official.cufitapi.domain.application

import com.official.cufitapi.domain.api.dto.MemberInfoResponse
import com.official.cufitapi.domain.api.dto.MemberProfileRequest
import com.official.cufitapi.domain.infrastructure.repository.MemberJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberJpaRepository: MemberJpaRepository
) {

    fun getMemberInfo() : MemberInfoResponse {
        return MemberInfoResponse(
            name = "",
            email = ""
        )
    }

    fun updateMemberProfile(memberId: Long, request: MemberProfileRequest) {

    }

}