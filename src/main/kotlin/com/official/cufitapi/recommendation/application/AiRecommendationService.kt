package com.official.cufitapi.recommendation.application

import org.springframework.stereotype.Service

interface CandidateRecommendationInfoUseCase {
    fun send()
    fun getRecommendationResult()
}

@Service
class AiRecommendationService : CandidateRecommendationInfoUseCase {

    // TODO: AI 서버로부터 추천 결과 받아오기
    override fun getRecommendationResult() {
        TODO("Not yet implemented")
    }

    // TODO : AI 서버에 유저 정보 전송
    override fun send() {
        // TODO : SQS에 send
    }

}