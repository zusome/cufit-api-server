package com.official.cufitapi.domain.member.domain.repository

import com.official.cufitapi.domain.member.domain.Maker

interface MakerRepository {
    fun save(maker: Maker): Maker
}
