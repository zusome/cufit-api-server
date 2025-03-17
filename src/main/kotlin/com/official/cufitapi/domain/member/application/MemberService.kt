package com.official.cufitapi.domain.member.application

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.member.application.command.RegisterMemberCommand
import com.official.cufitapi.domain.member.application.command.UpdateMemberRealNameCommand
import com.official.cufitapi.domain.member.domain.Member
import com.official.cufitapi.domain.member.domain.repository.MemberRepository
import org.springframework.stereotype.Service

interface RegisterMemberUseCase {
    fun register(memberRegisterCommand: RegisterMemberCommand): Member
}

interface UpdateAuthorityMemberUseCase {
    fun updateMatchMaker(memberId: Long)
    fun updateMatchCandidate(memberId: Long)
}

interface UpdateMemberRealNameUseCase {
    fun updateRealName(command: UpdateMemberRealNameCommand): Member
}

interface FindMemberUseCase {
    fun findById(memberId: Long): Member
}

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) : RegisterMemberUseCase, UpdateAuthorityMemberUseCase, UpdateMemberRealNameUseCase, FindMemberUseCase {

    override fun register(memberRegisterCommand: RegisterMemberCommand): Member {
        return memberRepository.findByProviderAndProviderId(
            memberRegisterCommand.provider,
            memberRegisterCommand.providerId
        ) ?: memberRepository.save(memberRegisterCommand.init())
    }

    override fun findById(memberId: Long): Member =
        memberRepository.findById(memberId)

    override fun updateRealName(command: UpdateMemberRealNameCommand): Member {
        val member = memberRepository.findByIdOrNull(command.memberId)
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        member.updateName(command.name)
        return memberRepository.save(member)
    }

    override fun updateMatchMaker(memberId: Long) {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        member.updateMatchMaker()
        memberRepository.save(member)
    }

    override fun updateMatchCandidate(memberId: Long) {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        member.updateMatchCandidate()
        memberRepository.save(member)
    }
}

