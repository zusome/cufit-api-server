package com.official.cufitapi.domain.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.api.dto.connection.ConnectionApplyRequest
import com.official.cufitapi.domain.enums.MatchStatus
import com.official.cufitapi.domain.infrastructure.entity.MatchConnection
import com.official.cufitapi.domain.infrastructure.repository.MatchCandidateJpaRepository
import com.official.cufitapi.domain.infrastructure.repository.MatchConnectionJpaRepository
import org.springdoc.webmvc.core.service.RequestService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConnectionService(
    private val candidateJpaRepository: MatchCandidateJpaRepository,
    private val requestService: RequestService,
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

    fun reject() {
        // 연결 요청 제거
        // push
    }
}