package com.official.cufitapi.domain.member.api

import com.official.cufitapi.domain.invitation.domain.event.AcceptedInvitationCardEvent
import com.official.cufitapi.domain.member.application.RegisterMatchCandidateUseCase
import com.official.cufitapi.domain.member.application.RegisterMatchMakerUseCase
import com.official.cufitapi.domain.member.application.RegisterMemberRelationUseCase
import com.official.cufitapi.domain.member.application.UpdateAuthorityMemberUseCase
import com.official.cufitapi.domain.member.domain.vo.MemberType
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MemberInternalEventHandler(
    private val registerMemberRelationUseCase: RegisterMemberRelationUseCase,
    private val updateAuthorityMemberUseCase: UpdateAuthorityMemberUseCase,
    private val registerMatchMakerUseCase: RegisterMatchMakerUseCase,
    private val registerMatchCandidateUseCase: RegisterMatchCandidateUseCase

) {

    @EventListener
    fun handleEvent(event: AcceptedInvitationCardEvent) {
        val memberRelation = registerMemberRelationUseCase.register(event.inviterId, event.inviteeId, event.relationType)
        if(event.code == "a123456") {
            updateAuthorityMemberUseCase.updateMatchMaker(event.inviteeId)
            registerMatchMakerUseCase.register(event.inviteeId)
            return
        }
        if(event.code == "b123456") {
            updateAuthorityMemberUseCase.updateMatchCandidate(event.inviteeId)
            registerMatchCandidateUseCase.register(event.inviteeId)
            return
        }
        val memberType = event.code.substring(0, 2).let(MemberType.Companion::ofCode)
        when (memberType) {
            MemberType.CANDIDATE -> {
                updateAuthorityMemberUseCase.updateMatchCandidate(event.inviteeId)
                registerMatchCandidateUseCase.register(event.inviteeId)
            }
            MemberType.MATCHMAKER -> {
                updateAuthorityMemberUseCase.updateMatchMaker(event.inviteeId)
                registerMatchMakerUseCase.register(event.inviteeId)
            }
            else -> {}
        }
    }
}
