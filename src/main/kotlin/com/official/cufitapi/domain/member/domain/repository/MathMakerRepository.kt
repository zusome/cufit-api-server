package com.official.cufitapi.domain.member.domain.repository

import com.official.cufitapi.domain.member.domain.MatchMaker

interface MathMakerRepository {
    fun save(matchMaker: MatchMaker): MatchMaker
}
