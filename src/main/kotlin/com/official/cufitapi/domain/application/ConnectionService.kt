package com.official.cufitapi.domain.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.api.dto.connection.ConnectionApplyRequest
import com.official.cufitapi.domain.api.dto.connection.ConnectionUpdateRequest
import com.official.cufitapi.domain.enums.MatchStatus
import com.official.cufitapi.domain.infrastructure.entity.MatchConnection
import com.official.cufitapi.domain.infrastructure.repository.MatchCandidateJpaRepository
import com.official.cufitapi.domain.infrastructure.repository.MatchConnectionJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConnectionService(
    private val candidateJpaRepository: MatchCandidateJpaRepository,
    private val matchConnectionJpaRepository: MatchConnectionJpaRepository
) {

    @Transactional
    fun apply(memberId: Long, request: ConnectionApplyRequest) {
        // 요청 개수 확인 후 3개 보다 많으면 요청 Reject
        val count = candidateJpaRepository.countByMatchMakerIdAndSenderIdAndCreatedDate(
            matchMakerId = request.matchMakerId,
            senderId = request.senderId)
        if (count > 3) {
            throw InvalidRequestException("요청은 하루에 3개 이상 보낼 수 없습니다.")
        }

        val sender = candidateJpaRepository.findMemberByMatchCandidateId(request.senderId)
            ?: throw InvalidRequestException("존재하지 않는 Sender")
        val receiver = candidateJpaRepository.findMemberByMatchCandidateId(request.receiverId)
            ?: throw InvalidRequestException("존재하지 않는 Receiver")

        // 성별 확인
        if (sender.gender != receiver.gender) {
            throw InvalidRequestException("Sender와 Receiver의 성별이 일치하지 않습니다.")
        }

        // 요청 정보 DB에 저장
        val connection = MatchConnection(
            matchMakerId =  request.matchMakerId,
            receiverId = request.receiverId,
            senderId = request.senderId,
            status = MatchStatus.PROGRESSING
        )
        matchConnectionJpaRepository.save(connection)

        // TODO : 알림
    }

    @Transactional
    fun updateConnectionStatus(connectionId: Long, request: ConnectionUpdateRequest) {
        val connection = matchConnectionJpaRepository.findByIdOrNull(connectionId)
            ?: throw InvalidRequestException("존재하지 않는 매칭연결")
        connection.updateStatus(request.matchStatus)
    }

    fun getReceivedConnections(candidateId: Long) {
        // TODO: MatchStatus=MatchStatus.Progressing 요청 중에 내가 받은 요청만 조회
        matchConnectionJpaRepository.findAllByReceiverIdAndStatusOrderByCreatedDate(candidateId, MatchStatus.PROGRESSING)
    }

    fun getMatchResults() {
        // TODO: MatchStatus=MatchStatus.Progressing 요청 중에 내가 보낸 요청만 조회
        // TODO: MatchStatus=MatchStatus.REJECTED
        // TODO: MatchStatus=MatchStatus.ACCEPTED -> 전화번호 값을 채워서 return
    }
}