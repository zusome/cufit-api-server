package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.domain.MatchMaker
import com.official.cufitapi.domain.member.domain.repository.MathMakerRepository
import org.springframework.stereotype.Service

interface RegisterMatchMakerUseCase {
    fun register(inviteeId: Long): MatchMaker
}

@Service
class MatchMakerService(
    private val matchMakerRepository: MathMakerRepository,
) : RegisterMatchMakerUseCase {

    override fun register(memberId: Long): MatchMaker {
        return matchMakerRepository.save(MatchMaker(memberId))
    }
}
