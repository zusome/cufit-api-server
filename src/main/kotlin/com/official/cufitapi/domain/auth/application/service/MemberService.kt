package com.official.cufitapi.domain.auth.application.service

import com.official.cufitapi.domain.auth.application.FindMemberUseCase
import com.official.cufitapi.domain.auth.application.RegisterMemberUseCase
import com.official.cufitapi.domain.auth.application.command.RegisterMemberCommand
import com.official.cufitapi.domain.auth.domain.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.repository.AuthorizationMemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val authorizationMemberRepository: AuthorizationMemberRepository,
) : RegisterMemberUseCase, FindMemberUseCase {

    override fun register(command: RegisterMemberCommand): AuthorizationMember {
        return authorizationMemberRepository.register(command.toDomain())
    }

    override fun findById(memberId: Long): AuthorizationMember {
        return authorizationMemberRepository.findById(memberId)
    }
}
