package com.official.cufitapi.domain.application

import com.official.cufitapi.domain.infrastructure.persistence.MatchCandidateJpaRepository
import org.springframework.stereotype.Service

@Service
class CandidateQueryService(
    private val candidateJpaRepository: MatchCandidateJpaRepository
) {
//    fun getCandidates(matchMakerId: Long): List<CandidateResponse> {
//        val candidates = candidateJpaRepository.findAllCandidatesByMatchMaker(matchMakerId)
//        return candidates.map {
//            CandidateResponse(
//                candidateId = it.id!!,
//                images =
//                name = it.name,
//                age = it.age,
//                relation = it.relation,
//                matchMakerName = it.matchMakerName,
//                mbti = it.mbti,
//                height = it.height,
//                station = it.station,
//                job = it.job
//            )
//        }
//    }
//}

    fun getConnectionList() {

    }
}