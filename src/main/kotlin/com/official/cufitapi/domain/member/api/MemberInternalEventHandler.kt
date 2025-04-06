package com.official.cufitapi.domain.member.api

import com.official.cufitapi.domain.invitation.domain.event.AcceptedInvitationCardEvent
import com.official.cufitapi.domain.member.application.RegisterMatchCandidateUseCase
import com.official.cufitapi.domain.member.application.RegisterMakerUseCase
import com.official.cufitapi.domain.member.application.RegisterMemberRelationUseCase
import com.official.cufitapi.domain.member.application.UpdateAuthorityMemberUseCase
import com.official.cufitapi.domain.member.domain.vo.MemberType
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MemberInternalEventHandler(
    private val registerMemberRelationUseCase: RegisterMemberRelationUseCase,
    private val updateAuthorityMemberUseCase: UpdateAuthorityMemberUseCase,
    private val registerMakerUseCase: RegisterMakerUseCase,
    private val registerMatchCandidateUseCase: RegisterMatchCandidateUseCase

) {

    @EventListener
    fun handleEvent(event: AcceptedInvitationCardEvent) {
        val memberRelation = registerMemberRelationUseCase.register(event.inviterId, event.inviteeId, event.relationType)
        if(event.code == "a123456") {
            updateAuthorityMemberUseCase.updateMaker(event.inviteeId)
            registerMakerUseCase.register(event.inviteeId)
            return
        }
        if(event.code == "b123456") {
            updateAuthorityMemberUseCase.updateCandidate(event.inviteeId)
            registerMatchCandidateUseCase.register(event.inviteeId)
            return
        }
        val memberType = event.code.substring(0, 2).let(MemberType.Companion::ofCode)
        when (memberType) {
            MemberType.CANDIDATE -> {
                updateAuthorityMemberUseCase.updateCandidate(event.inviteeId)
                registerMatchCandidateUseCase.register(event.inviteeId)
            }
            MemberType.MAKER -> {
                updateAuthorityMemberUseCase.updateMaker(event.inviteeId)
                registerMakerUseCase.register(event.inviteeId)
            }
            else -> {}
        }
    }
}
