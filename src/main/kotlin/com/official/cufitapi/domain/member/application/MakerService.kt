package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.domain.Maker
import com.official.cufitapi.domain.member.domain.repository.MakerRepository
import org.springframework.stereotype.Service

interface RegisterMakerUseCase {
    fun register(inviteeId: Long): Maker
}

@Service
class MakerService(
    private val makerRepository: MakerRepository,
) : RegisterMakerUseCase {

    override fun register(memberId: Long): Maker {
        return makerRepository.save(Maker(memberId))
    }
}
