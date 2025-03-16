package com.official.cufitapi.domain.member.api

import com.official.cufitapi.domain.invitation.domain.event.InvitationAcceptEvent
import com.official.cufitapi.domain.member.application.RegisterMatchCandidateUseCase
import com.official.cufitapi.domain.member.application.RegisterMatchMakerUseCase
import com.official.cufitapi.domain.member.application.RegisterMemberRelationUseCase
import com.official.cufitapi.domain.member.domain.vo.MemberType
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MemberInternalEventHandler(
    private val registerMemberRelationUseCase: RegisterMemberRelationUseCase,
    private val registerMatchMakerUseCase: RegisterMatchMakerUseCase,
    private val registerMatchCandidateUseCase: RegisterMatchCandidateUseCase

) {

    @EventListener
    fun handleEvent(event: InvitationAcceptEvent) {
        val memberRelation = registerMemberRelationUseCase.register(event.inviterId, event.inviteeId, event.relationType)
        if(event.code == "a123456") {
            registerMatchMakerUseCase.register(event.inviteeId)
            return
        }
        if(event.code == "b123456") {
            registerMatchCandidateUseCase.register(event.inviteeId)
            return
        }
        val memberType = event.code.substring(0, 2).let(MemberType.Companion::ofCode)
        when (memberType) {
            MemberType.CANDIDATE -> registerMatchCandidateUseCase.register(event.inviteeId)
            MemberType.MATCHMAKER -> registerMatchMakerUseCase.register(event.inviteeId)
            else -> {}
        }
    }
}
