package com.official.cufitapi.domain.connection.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.connection.application.command.ConnectionApplyCommand
import com.official.cufitapi.domain.connection.application.command.ConnectionUpdateCommand
import com.official.cufitapi.domain.connection.infrastructure.persistence.MatchConnectionEntity
import com.official.cufitapi.domain.connection.infrastructure.persistence.MatchConnectionJpaRepository
import com.official.cufitapi.domain.connection.domain.vo.MatchStatus
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberProfileImageJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConnectionService(
    private val candidateJpaRepository: MatchCandidateJpaRepository,
    private val matchConnectionJpaRepository: MatchConnectionJpaRepository,
    private val memberProfileImageJpaRepository: MemberProfileImageJpaRepository
) {

    @Transactional
    fun apply(command: ConnectionApplyCommand) {
        // 요청 개수 확인 후 3개 보다 많으면 요청 Reject
        val count = candidateJpaRepository.countByMatchMakerIdAndSenderIdAndCreatedDate(
            matchMakerId = command.matchMakerId,
            senderId = command.senderId
        )
        if (count > 3) {
            throw InvalidRequestException("요청은 하루에 3개 이상 보낼 수 없습니다.")
        }

        val sender = candidateJpaRepository.findMemberByMatchCandidateId(command.senderId)
            ?: throw InvalidRequestException("존재하지 않는 Sender")
        val receiver = candidateJpaRepository.findMemberByMatchCandidateId(command.receiverId)
            ?: throw InvalidRequestException("존재하지 않는 Receiver")

        // 성별 확인
        if (sender.gender != receiver.gender) {
            throw InvalidRequestException("Sender와 Receiver의 성별이 일치하지 않습니다.")
        }

        // 요청 정보 DB에 저장
        val connection = MatchConnectionEntity(
            matchMakerId = command.matchMakerId,
            receiverId = command.receiverId,
            senderId = command.senderId,
            status = MatchStatus.PROGRESSING
        )
        matchConnectionJpaRepository.save(connection)

        // TODO : 알림
    }

    @Transactional
    fun updateConnectionStatus(command: ConnectionUpdateCommand) {
        val connection = matchConnectionJpaRepository.findByIdOrNull(command.connectionId)
            ?: throw InvalidRequestException("존재하지 않는 매칭연결")
        connection.updateStatus(MatchStatus.of(command.matchStatus))
    }

//    fun getReceivedConnections(candidateId: Long): List<ReceivedConnectionResponse> {
//        // TODO: MatchStatus=MatchStatus.Progressing 요청 중에 내가 받은 요청만 조회
//        return matchConnectionJpaRepository.findAllByReceiverIdAndStatusOrderByCreatedDate(candidateId, MatchStatus.PROGRESSING)
//            .map {
//                val senderInfo = candidateJpaRepository.findMemberByMatchCandidateId(it.senderId)
//                    ?: throw InvalidRequestException("존재하지 않는 Member")
//                val profileImage = memberProfileImageJpaRepository.findAllByMemberId(senderInfo.memberId)
//
//                return ReceivedConnectionResponse(
//                    candidateId = senderInfo.id,
//                    height = senderInfo.height,
//                    idealAge =   senderInfo.idealAgeRange List<IdealAge>,
//                    idealHeight: List<IdealHeightUnit>,
//                    profileImages: List<ImageResponse>,
//                )
//
//            }

//    }

    fun getMatchResults() {
        // TODO: MatchStatus=MatchStatus.Progressing 요청 중에 내가 보낸 요청만 조회
        // TODO: MatchStatus=MatchStatus.REJECTED
        // TODO: MatchStatus=MatchStatus.ACCEPTED -> 전화번호 값을 채워서 return
    }
}