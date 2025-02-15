package com.official.cufitapi.domain.recommendation.domain

interface UserInfoAiClient {
    fun register(userInfo: AiUserInfo)
    fun recommend(): AiUserInfo
}