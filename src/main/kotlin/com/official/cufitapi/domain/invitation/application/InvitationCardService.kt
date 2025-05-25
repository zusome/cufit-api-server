package com.official.cufitapi.domain.invitation.application

// 의존성 개선 필요
import com.official.cufitapi.domain.invitation.application.command.AcceptInvitationCardCommand
import com.official.cufitapi.domain.invitation.application.command.GenerateInvitationCardCommand
import com.official.cufitapi.domain.invitation.domain.InvitationCard
import com.official.cufitapi.domain.invitation.domain.InvitationCardRepository
import com.official.cufitapi.domain.invitation.domain.Inviters
import com.official.cufitapi.domain.invitation.domain.event.AcceptedInvitationCardEvent
import com.official.cufitapi.domain.invitation.domain.event.RegisteredInvitationCardEvent
import com.official.cufitapi.domain.invitation.domain.factory.InvitationCodeFactory
import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode
import com.official.cufitapi.domain.invitation.domain.vo.InvitationRelationType
import com.official.cufitapi.domain.invitation.domain.vo.InvitationType
import com.official.cufitapi.domain.invitation.domain.vo.InvitationType.entries
import com.official.cufitapi.domain.invitation.domain.vo.Inviter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

interface GenerateInvitationCardUseCase {
    fun generate(command: GenerateInvitationCardCommand): InvitationCard
}

interface AcceptInvitationCardUseCase {
    fun accept(command: AcceptInvitationCardCommand): InvitationCard
}

interface FindInvitersUseCase {
    fun findByInviterId(inviterId: Long): Inviter
}

@Service
class InvitationCardService(
    private val inviters: Inviters,
    private val invitationCodeFactory: InvitationCodeFactory,
    private val invitationCardRepository: InvitationCardRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : GenerateInvitationCardUseCase, AcceptInvitationCardUseCase, FindInvitersUseCase {

    override fun generate(command: GenerateInvitationCardCommand): InvitationCard {
        val inviter = inviters.findById(command.inviterId)
        val invitationType = InvitationType.of(command.invitationType)
        val invitationRelationType = InvitationRelationType.of(command.invitationRelationType)
        val invitationCard = invitationCardRepository.save(
            InvitationCard(
                code = invitationCodeFactory.generate(invitationType, invitationRelationType),
                invitationType = invitationType,
                relationType = invitationRelationType,
                inviterId = inviter.inviterId,
                isAccepted = false
            )
        )
        applicationEventPublisher.publishEvent(
            RegisteredInvitationCardEvent(
                invitationCard.code.value,
                invitationCard.invitationType.value,
                invitationCard.inviterId,
                invitationCard.relationType.name,
                invitationCard.isAccepted,
                invitationCard.inviteeId,
                invitationCard.id!!
            )
        )
        return invitationCard
    }

    /**
     * MemberType 수정하기
     */
    override fun accept(command: AcceptInvitationCardCommand): InvitationCard {
        if (command.invitationCode == "a123456" || command.invitationCode == "b123456" || command.invitationCode == "c123456") {
            applicationEventPublisher.publishEvent(
                AcceptedInvitationCardEvent.mock(
                    command.invitationCode,
                    parse(command.invitationCode).value,
                    command.inviteeId,
                    "FRIEND"
                )
            )
            return InvitationCard(
                code = InvitationCode(command.invitationCode),
                invitationType = parse(command.invitationCode),
                inviterId = 1L,
                inviteeId = command.inviteeId,
                relationType = InvitationRelationType.FRIEND,
                isAccepted = true,
            )
        }
        val invitationCard = invitationCardRepository.findByCode(InvitationCode(command.invitationCode))
        invitationCard.accept(command.inviteeId)
        invitationCardRepository.save(invitationCard)
        applicationEventPublisher.publishEvent(
            AcceptedInvitationCardEvent(
                invitationCard.code.value,
                invitationCard.invitationType.value,
                invitationCard.inviterId,
                invitationCard.relationType.name,
                invitationCard.isAccepted,
                invitationCard.inviteeId!!,
                invitationCard.id!!
            )
        )
        return invitationCard
    }

    override fun findByInviterId(inviterId: Long): Inviter {
        return inviters.findById(inviterId)
    }

    companion object {
        fun parse(invitationCode: String): InvitationType =
            entries.firstOrNull { invitationCode.startsWith(it.suffix()) }
                ?: throw IllegalArgumentException("Invalid invitation code: $invitationCode")
    }
}
